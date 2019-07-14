package cn.enjoyedu.sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com
 *
 *往期视频咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：贵金属期货的实现
 */
@Controller
public class NobleMetalController {

    private static Logger logger = LoggerFactory.getLogger(NobleMetalController.class);

    @RequestMapping("/nobleMetal")
//  fxc-SSE会自动发起重连， return 后就把请求交给了tomcat，自动关闭了连接
    public String stock(){
        return "nobleMetal";
    }

//    fxc-produces="text/event-stream 这里很重要， 告诉浏览器推送的是事件流，不能关闭连接
    @RequestMapping(value="/needPrice"
            ,produces="text/event-stream;charset=UTF-8"
            )
    @ResponseBody
    public String push(){
        Random r = new Random();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return makeResp(r);

    }

    /*业务方法，生成贵金属的实时价格*/
    private String makeResp(Random r){
        StringBuilder stringBuilder = new StringBuilder("");
//       fxc- \n 代表一个数据项； \n\n 代表数据完了。
        stringBuilder.append("retry:2000\n")
                .append("data:")
                .append(r.nextInt(100)+50+",")
                .append(r.nextInt(40)+35)
                .append("\n\n");
        return stringBuilder.toString();
    }








    /*------------以下为正确使用SSE的姿势------------------*/
//    fxc-使用流 flush的方式 写数据到浏览器！
    @RequestMapping("/nobleMetalr")
    public String stockr(){
        return "nobleMetalAlso";
    }

    @RequestMapping(value="needPricer")
    public void pushRight(HttpServletResponse response){
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        Random r = new Random();
        try {
            PrintWriter pw = response.getWriter();
            int i = 0;
            while(i<10){
                if(pw.checkError()){
                    System.out.println("客户端断开连接");
                    return;
                }
                Thread.sleep(1000);
                pw.write(makeResp(r));
                pw.flush();
                i++;
            }
            System.out.println("达到阈值，结束发送.......");
            pw.write("data:stop\n\n");
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
