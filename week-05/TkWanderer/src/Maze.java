import java.awt.*;
import java.util.Optional;

class Maze {
    static final Tile WALL = new Tile("wall.gif",true);
    static final Tile FLOOR = new Tile("floor.gif",false);

    private Tile[][] data;
    private java.util.Random rand = new java.util.Random();

    static final int MAZE_WIDTH = 13;
    static final int MAZE_HEIGHT = 9;

    public Maze() {
        data = new Tile[MAZE_WIDTH][MAZE_HEIGHT];
    }

    private void carve(int x, int y) {

        final int[] moveX = { 1, -1, 0, 0 };
        final int[] moveY = { 0, 0, 1, -1 };

        int dir = rand.nextInt(4);
        int count = 0;

        while(count < 4) {
            final int x1 = x + moveX[dir];
            final int y1 = y + moveY[dir];
            final int x2 = x1 + moveX[dir];
            final int y2 = y1 + moveY[dir];
            if(getTile(x2, y2).orElse(FLOOR).isSolid) {
                data[x1][y1] = FLOOR;
                data[x2][y2] = FLOOR;
                carve(x2, y2);
            } else {
                dir = (dir + 1) % 4;
                count += 1;
            }
        }
    }

    public void generate() {
        for(int x = 0; x < MAZE_WIDTH; x++) {
            data[x] = new Tile[MAZE_HEIGHT];
            for(int y = 0; y < MAZE_HEIGHT; y++) {
                data[x][y] = WALL;
            }
        }

        data[0][0] = FLOOR;
        carve(0, 0);
    }

    public Optional<Tile> getTile(int x, int y){
        if(x < 0 || x > MAZE_WIDTH - 1 || y < 0 || y > MAZE_HEIGHT - 1) {
            return Optional.empty();
        } else {
            return Optional.of(data[x][y]);
        }
    }

    public void drawBackground(Graphics g){
        for(int i = 0; i < MAZE_WIDTH; i ++){
            for (int j = 0; j < MAZE_HEIGHT; j ++){
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(getTile(i,j).orElse(FLOOR).getTexture(),i * Tile.tileSize ,j * Tile.tileSize , null);
            }
        }
    }

}

