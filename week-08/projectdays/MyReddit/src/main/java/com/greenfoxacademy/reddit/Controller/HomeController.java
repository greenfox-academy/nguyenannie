package com.greenfoxacademy.reddit.Controller;

import com.greenfoxacademy.reddit.Model.Pager;
import com.greenfoxacademy.reddit.Model.Post;
import com.greenfoxacademy.reddit.Model.User;
import com.greenfoxacademy.reddit.Service.PostServiceDbImpl;
import com.greenfoxacademy.reddit.Service.UserServiceDbImpl;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {
    private static final int NUM_OF_BUTTONS = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;
    private static final int[] PAGE_SIZES = {5, 10, 15};

    private final PostServiceDbImpl postServiceDb;
    private final UserServiceDbImpl userServiceDb;

    @Autowired
    public HomeController(PostServiceDbImpl postServiceDb, UserServiceDbImpl userServiceDb) {
        this.postServiceDb = postServiceDb;
        this.userServiceDb = userServiceDb;
    }

    @GetMapping("/home/{username}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getHome(Model model, HttpServletRequest request,
                          @PathVariable(value = "username") String username,
                          @RequestParam("pageSize") Optional<Integer> pageSize,
                          @RequestParam("page") Optional<Integer> page) {

        User user = userServiceDb.findByName(username);

        int setPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int setPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Post> posts =
                postServiceDb.findByPage(new PageRequest(setPage, setPageSize, Sort.Direction.DESC, "score"));
        Pager pager = new Pager(posts.getTotalPages(), posts.getNumber(), NUM_OF_BUTTONS);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("pager", pager);
        model.addAttribute("selectedPageSize", setPageSize);
        model.addAttribute("pageSizes", PAGE_SIZES);

        return "home";
    }

}
