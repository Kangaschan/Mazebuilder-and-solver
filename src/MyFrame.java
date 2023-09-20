import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyFrame extends JFrame implements KeyListener {
    Image path_tile;
    Image exit;
    Image bed;
    Image ledder;
    Image elevator;
    Image lab;
    Image med;
    Image wallh;
    Image tile;
    Image wall;
    Image floor2;
    Image floor3;
    Tile[][] array = new Tile[10][10];
    int i_start,j_start,start_p;
    final int TILE = 0;
    final int PATH_TILE = -1;
    final int LEDDER = 1;
    final int ELEVETOR = 2;
    final int BED = 3;
    final int EXIT = 4;
    final int LAB = 5;
    final int MED = 6;
    private final int FLOOR2 =20;
    private final int FLOOR3 = 30;
    int el_i=0,el_j=4;
    int StartPoint;
    MyFrame(){
        Random myrandom = new Random();
            this.setTitle("лабиринт");
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setSize(440,440);
            this.setLocation(300,300);
            this.setVisible(true);
            this.addKeyListener(this);
            array = MatrixInit();
            add(new MazeField());
            array = MakeMaze(array);
            PlaceFacil();



            this.repaint();
        this.setVisible(true);
        }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case ('b'):
                StartPoint = BED;
                break;
            case ('m'):
                StartPoint = MED;
                break;
            case ('l'):
                StartPoint = LAB;
                break;
            default:

        }
        if ((e.getKeyChar()=='b')||(e.getKeyChar()=='m')||(e.getKeyChar()=='l')) {
            int d = ExitFounder(StartPoint);
            ExitMake(d);
            repaint();
        }
        if (e.getKeyChar()=='1'){
            StartPoint=ELEVETOR;
            array = MatrixInit();
            for(int i=0; i<10; ++i){
                for(int j=0; j<10; ++j){
                    array[i][j].tiletype=FLOOR2;
                }
            }
            array = MakeMaze(array);
            array[el_i][el_j].tiletype=ELEVETOR;
            array[0][4].tiletype=LEDDER;
            this.repaint();
            int d = ExitFounder(EXIT);
            ExitMake(d);
            repaint();
        }
        if (e.getKeyChar()=='2'){
            StartPoint=LEDDER;
            array = MatrixInit();
            for(int i=0; i<10; ++i){
                for(int j=0; j<10; ++j){
                    array[i][j].tiletype=FLOOR3;
                }
            }
            array = MakeMaze(array);
            Random k = new Random();
            boolean possible=true;
            int x1,y1,x2,y2;
            while (possible){
                x1=k.nextInt(10);
                y1=k.nextInt(10);
                x2=k.nextInt(10);
                y2=k.nextInt(10);
                if((x1!=x2) || (y1!=y2)){
                    possible=false;
                }
                array[x1][y1].tiletype=LEDDER;
                array[x2][y2].tiletype=EXIT;
            }

            this.repaint();
            int d = ExitFounder(EXIT);
            ExitMake(d);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
repaint();

    }
    public int ExitFounder(int exit_type) {
        int start_x, start_y;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (array[i][j].tiletype == StartPoint) {
                    start_y = j;
                    start_x = i;
                    array[i][j].length=0;
                    break;
                }
            }
        }
        boolean finish= false;
        int d = -1;


            while (!finish) {
                d++;
                for (int i = 0; i < 10; ++i) {
                    for (int j = 0; j < 10; ++j) {  //пускаем волну
                        if(array[i][j].length==d){

                            if ((array[i][j].rWall==false) && (i+1!=10) && (array[i+1][j].length==-5)){
                                array[i+1][j].length=d+1;
                                if ((array[i+1][j].tiletype==ELEVETOR)||(array[i+1][j].tiletype==LEDDER)||(array[i+1][j].tiletype==EXIT)){
                                    i_start=i+1;
                                    j_start=j;
                                    start_p=array[i_start][j_start].tiletype;
                                    finish=true;
                                    return d+1;
                                }
                            }
                            if ((array[i][j].lWall==false) && (i-1!=-1) && (array[i-1][j].length==-5)){
                                array[i-1][j].length=d+1;
                                if ((array[i-1][j].tiletype==ELEVETOR)||(array[i-1][j].tiletype==LEDDER)||(array[i-1][j].tiletype==EXIT)){
                                    i_start=i-1;
                                    j_start=j;
                                    start_p=array[i_start][j_start].tiletype;
                                    finish=true;
                                    return d+1;

                                }
                            }
                            if ((array[i][j].bWall==false) && (j+1!=10) && (array[i][j+1].length==-5)){
                                array[i][j+1].length=d+1;
                                if ((array[i][j+1].tiletype==ELEVETOR)||(array[i][j+1].tiletype==LEDDER)||(array[i][j+1].tiletype==EXIT)){
                                    i_start=i;
                                    j_start=j+1;
                                    start_p=array[i_start][j_start].tiletype;
                                    finish=true;return d+1;
                                }
                            }
                            if ((array[i][j].tWall==false) && (j-1!=-1) && (array[i][j-1].length==-5)){
                                array[i][j-1].length=d+1;
                                if ((array[i][j-1].tiletype==ELEVETOR)||(array[i][j-1].tiletype==LEDDER)||(array[i][j-1].tiletype==EXIT)){
                                    i_start=i;
                                    j_start=j-1;
                                    start_p=array[i_start][j_start].tiletype;
                                    finish=true;return d+1;
                                }
                            }


                            }
                        }
                    }
                }

        return d;
    }

    public void ExitMake(int d){
        int x=0,y=0,len = d;
//        for (int i = 0; i<10; i++){
//            for (int j = 0; j<10; j++){
//                if(array[i][j].length==d){
//                    x=i;
//                    y=j;
//                    break;
//                }
//            }
//        }
            x=i_start;
            y=j_start;

        while(len > 1){
            if((x!= 0) && (array[x-1][y].length == len - 1) && (array[x][y].lWall==false)){
                array[x-1][y].tiletype=PATH_TILE;
                len--;
                x--;
            }
                else {
                if((x!= 9) && (array[x+1][y].length == len - 1)&& (array[x][y].rWall==false)){
                    array[x+1][y].tiletype=PATH_TILE;
                    len--;
                    x++;
                }
                    else{
                    if((y!= 0) && (array[x][y-1].length == len - 1)&& (array[x][y].tWall==false)){
                        array[x][y-1].tiletype=PATH_TILE;
                        len--;
                        y--;
                    }
                        else{
                            if((y!= 9) && (array[x][y+1].length == len - 1)&& (array[x][y].bWall==false)) {
                                array[x][y+1].tiletype = PATH_TILE;
                                len--;
                                y++;
                            }
                        }
                    }
                }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public Tile[][] MatrixInit() {
        Tile res[][] = new Tile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                res[i][j] = new Tile();
            }
        }
        return res;
    }


    public boolean Possible(int k, int i, int j) {
        boolean res = false;
        if (k == 0) {
            if (j != 0) {
                res = true;
            }
        }
        if (k == 1) {
            if (i != 9) {
                res = true;
            }
        }
        return res;
    }

    public boolean Empty(int x, int y,Tile[][] array) {
        boolean res = true;
        if (array[x][y].tiletype != TILE) {
            res = false;
        }
        return res;
    }

    public void PlaceFacil(){
        Random myrandom = new Random();
        int x,y,i = 1;
        while (i < 7) {
            x = myrandom.nextInt(10);
            y = myrandom.nextInt(10);
            if (Empty(x, y,array)) {
                if ((i!=4) && (i!=1) && (i!=ELEVETOR)) {
                    array[x][y].tiletype = i;
                }
                i++;
            }
        }
        while ((el_i==0) && (el_j==4)){
            el_i = myrandom.nextInt(10);
            el_j = myrandom.nextInt(10);}
        array[el_i][el_j].tiletype=ELEVETOR;
    }

    public Tile[][] MakeMaze(Tile[][] arra) {
        Random myrandom = new Random();
        Tile[][] array = new Tile[10][10];
        array = arra;
        int x = 0, y = 0, k;
        while (y != 10) {
            k = myrandom.nextInt(2); // 0-вверх 1-вправо
            if (k == 0) {
                if (Possible(k, x, y)) {
                    array[x][y].tWall = false;
                    array[x][y - 1].bWall = false;
                } else {
                    if (Possible(1, x, y)) {
                        array[x][y].rWall = false;
                        array[x + 1][y].lWall = false;
                    }
                }
            }
            if (k == 1) {
                if (Possible(k, x, y)) {
                    array[x][y].rWall = false;
                    array[x + 1][y].lWall = false;
                } else {
                    if (Possible(0, x, y)) {
                        array[x][y].tWall = false;
                        array[x][y - 1].bWall = false;
                    }
                }
            }
            x++;//переходим вправо
            if (x >= 10) {
                x = 0;
                y++;
            }

        }

        return array;
    }

     class MazeField extends JPanel{
        MazeField(){
            setBackground(Color.black);
            LoadImages();
            repaint();
        }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //отрисовка тайлов
                switch (array[i][j].tiletype) {
                    case (TILE):
                        g.drawImage(tile, 40 * i, 40 * j, this);
                        continue;
                    case (PATH_TILE):
                        g.drawImage(path_tile, 40 * i, 40 * j, this);
                        continue;
                    case (BED):
                        g.drawImage(bed, 40 * i, 40 * j, this);
                        continue;
                    case (ELEVETOR):
                        g.drawImage(elevator, 40 * i, 40 * j, this);
                        continue;
                    case (LAB):
                        g.drawImage(lab, 40 * i, 40 * j, this);
                        continue;
                    case (LEDDER):
                        g.drawImage(ledder, 40 * i, 40 * j, this);
                        continue;
                    case (MED):
                        g.drawImage(med, 40 * i, 40 * j, this);
                        continue;
                    case (EXIT):
                        g.drawImage(exit, 40 * i, 40 * j, this);
                        continue;
                    case (FLOOR2):
                        g.drawImage(floor2, 40 * i, 40 * j, this);
                        continue;
                    case (FLOOR3):
                        g.drawImage(floor3, 40 * i, 40 * j, this);
                }
                //continue;
            }// отрисовка тайла в зависимотси от типа
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (array[i][j].tWall == true) {
                    g.drawImage(wallh, 40 * i, 40 * j, this);
                }
                if (array[i][j].bWall == true) {
                    g.drawImage(wallh, 40 * i, 40 * j + 36, this);
                }
                if (array[i][j].lWall == true) {
                    g.drawImage(wall, 40 * i, 40 * j, this);
                }
                if (array[i][j].rWall == true) {
                    g.drawImage(wall, 40 * i + 36, 40 * j, this);
                }
            }
        }

    }

     public void LoadImages() {//C:\Everything\Prog\JavaProj\aisd 1\TRY 2\src\images\bed.png
        ImageIcon iit = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\tile.png");
        tile = iit.getImage();
        ImageIcon iiw = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\wall.png");
        wall = iiw.getImage();
        ImageIcon iip = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\path_tile.png");
        path_tile = iip.getImage();
        ImageIcon iib = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\bed.png");
        bed = iib.getImage();
        ImageIcon iie = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\exit.png");
        exit = iie.getImage();
        ImageIcon iielv = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\elevator.png");
        elevator = iielv.getImage();
        ImageIcon iil = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\ledder.png");
        ledder = iil.getImage();
        ImageIcon iim = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\med.png");
        med = iim.getImage();
        ImageIcon iilab = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\lab.png");
        lab = iilab.getImage();
        ImageIcon iiwallh = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\wallh.png");
        wallh = iiwallh.getImage();
         ImageIcon fl2 = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\2floor.png");
         floor2 = fl2.getImage();
         ImageIcon fl3 = new ImageIcon("//C:\\Everything\\Prog\\JavaProj\\aisd 1\\TRY 2\\src\\images\\3floor.png");
         floor3 = fl3.getImage();
    }
    }
}
