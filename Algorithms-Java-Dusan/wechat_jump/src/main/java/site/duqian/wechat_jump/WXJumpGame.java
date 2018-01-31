package site.duqian.wechat_jump;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class WXJumpGame extends JFrame {

    private static final long serialVersionUID = 1L;

    private PhoneImagePanel phoneImgPanel = null;
    private boolean isFirst = true;
    private Point prePoint = new Point();
    private Point curPoint = new Point();

    public WXJumpGame() {
        phoneImgPanel = new PhoneImagePanel();
        this.add(phoneImgPanel);
        this.setSize(360, 640);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //给图像面板添加鼠标点击事件
        phoneImgPanel.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent event) {
                java.awt.Point point = event.getLocationOnScreen();
                if (point.x < 250 && point.y < 250) {
                    isFirst = true;
                    refreshView();
                    System.out.println("Screen captured,position x="+point.x+",y="+point.y);
                    return;
                }
                //每次在跳动之前，需要点击球柱的起跳点，也就是中心点
                if (isFirst) {
                    prePoint.x = event.getX();
                    prePoint.y = event.getY();
                    isFirst = false;
                    return;
                }
                curPoint.x = event.getX();
                curPoint.y = event.getY();
                //使用勾股定理计算跳跃的距离
                int distance = Utils.calDistance(prePoint, curPoint);
                //这个定值是需要手动调解出来的，每个手机尺寸或许不一样，需要自己手动调节
                int time = (int) (distance / 0.24);//0.37
                Utils.jump(time);
                System.out.println("distance:" + distance + ",time:" + time);
                //这里的时间是为了等待截图绘制图片到面板中，时间也是需要自己设定，不然图像绘制会出现错乱
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //为了下一次能够点击起跳点
                isFirst = true;
                //跳完了，就开始刷新面板，获取最新的手机屏幕信息
                refreshView();
            }

            @Override
            public void mouseReleased(MouseEvent event) {
            }

            @Override
            public void mouseExited(MouseEvent event) {

            }

            @Override
            public void mouseEntered(MouseEvent event) {

            }

            @Override
            public void mouseClicked(MouseEvent event) {
            }
        });
    }

    private void refreshView() {
        phoneImgPanel.validate();
        phoneImgPanel.repaint();
    }

    public static void main(String[] args) {
        new WXJumpGame();
    }

}

