package DrawGuess.Server;

import DrawGuess.Bag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class GameThread {
    private static String[] objlist= {
            "鸽子", "布娃娃", "餐巾", "仓库", "光盘", "瓷器", "长江三峡", "长颈漏斗", "赤壁",
            "除草剂", "大树", "刀", "冬瓜", "豆沙包", "耳机", "飞碟", "工资", "荷花", "烘干机",
            "蝴蝶", "护膝", "花朵", "欢乐谷", "击剑", "监狱", "教师", "结婚证", "狙击步枪",
            "空格键", "棉花", "母亲", "牛奶糖", "牛肉干", "牛肉面", "秦始皇兵马俑",
            "全家桶", "沙僧", "圣经", "升旗", "实验室", "狮子座", "守门员", "项链", "手套",
            "水波", "土豆", "丸子", "鲜橙多", "鲜花", "小霸王", "腰带", "烟斗", "扬州炒饭",
            "衣橱", "医生", "音响", "鹦鹉", "羊", "水龙头", "医用棉签", "潜水艇", "钻戒"
    };

    private static String[] objhint= {
            "家禽", "玩具", "生活用品", "建筑物", "电子产品", "装饰品", "建筑物", "器皿", "地名",
            "生活用品", "植物", "生活用品", "食物", "食物", "电子产品", "玩具", "生活用品", "植物", "电子产品",
            "动物", "生活用品", "植物", "地名", "体育运动", "建筑物", "职业", "生活用品", "武器",
            "电子产品", "植物", "职业", "食物", "食物", "食物", "建筑物",
            "食物", "职业", "生活用品", "行为", "建筑物", "天文星象", "职业", "服饰", "服饰",
            "自然现象", "植物", "食物", "食物", "植物", "电子产品", "服饰", "生活用品", "食物",
            "家具", "职业", "电子产品", "动物", "家禽", "生活用品", "医疗器具", "交通工具", "服饰"
    };
    private static int currpos = 0;
    private static boolean isGaming = false;

    public static void startGame(int number) {

        isGaming = true;
        ArrayList<Integer> uids = ServerSender.getUids();
        Iterator<Integer> i = uids.iterator();
        // for (int i = 0; i < number; i++) {
        while (i.hasNext()) {
            // 安排玩家
            currpos++;
            if(currpos > 62) currpos = 0;

            // 本轮游戏倒计时
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                int cnt = 61;

                @Override
                public void run() {
                    cnt--;
                    if (cnt <= 0) {
                        isGaming = false;
                        cancel();
                    }

                    // 发送词语及提示
                    if( cnt % 10 == 0 ){
                        try {
                            ServerSender.sendMessage(new Bag("HINT", "提示：这个词语有" + objlist[currpos].length() + "个字。", 3));
                        } catch (IOException e) {
                            System.out.println("Send Exception");
                        }
                    }

                    if( cnt % 10 == 4 ){
                        try {
                            ServerSender.sendMessage(new Bag("HINT", "提示：" + objhint[currpos], 3));
                        } catch (IOException e) {
                            System.out.println("Send Exception");
                        }
                    }
                }
            }, 0, 1000);
        }
    }

    public static String judge(String guessString) {
        // 返回的字符串为 "YES guessString" 或者 "NOP guessString"
        // 最好能把guessString里面跟答案一样的字替换成特殊的符号

        if( isGaming ) {
            String feedback = "";
            if (guessString.equals(objlist[currpos])) {
                for (int j = 0; j < objlist[currpos].length(); j++)
                    feedback += "6";
                return "YES " + feedback;
                //System.out.println("YES" + feedback);
            } else {
                for (int j = 0; j < objlist[currpos].length(); j++) {
                    if (guessString.charAt(j) == objlist[currpos].charAt(j))
                        feedback += "6";
                    else
                        feedback += guessString.charAt(j);
                }
                //System.out.println("NOP" + feedback);
                return "NOP " + feedback;
            }
        }
        else{
            return "    " + guessString;
        }
    }
}

