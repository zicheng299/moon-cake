package com.game;

import com.domain.Award;
import com.domain.Cake;
import com.domain.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入玩家人数:");
        int n = sc.nextInt();
        //生成 玩家姓名
        System.out.println("-------------------- 创建随机ID ----------------------");
        ArrayList<Player> players = setPlayer(n);
        ArrayList<Player> result = new ArrayList<>();//保存结果集合
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getIndex() + "号玩家：" + players.get(i).getName());
        }

        //测试分支代码合并
        sc.nextLine();
        sc.nextLine();
        //测试代码冲突
        System.out.println("-------------------- 设置奖池 ----------------------");
        ArrayList<Award> awards = setAwards();
        System.out.println("博饼\t奖品\t   数量");
        for (Award award : awards) {
            if (award.getName().length() > 7) {
                System.out.println(award.getCake() + "\t" + award.getName() + "\t" + award.getCount());
            } else if(award.getName().length() > 8) {
                System.out.println(award.getCake() + "\t" + award.getName() + "   " + award.getCount());
            }else {
                System.out.println(award.getCake() + "\t" + award.getName() + "\t\t" + award.getCount());
            }
        }
        sc.nextLine();
        System.out.println("-------------------- 开始游戏 ----------------------");
        int num = 1;//次数
        loop:
        while (true) {
            System.out.println("第" + num + "次:");
            sc.nextLine();
            int[] arr = get5Index(players);//随机抽5个人
            System.out.print("参与博饼的玩家号数：");
            printArray(arr);
            String start = sc.nextLine();
            //给每个玩家分饼
            for (Player player : players) {
                if (!isEqual(arr, player.getIndex())) {
                    continue;
                }
                ArrayList<Cake> cakeList = getCake();
                if ("开挂".equals(start)) {
                    System.out.print(player.getIndex() + "号玩家：" + player.getName()+"的点数设置为 ");
                    String point = sc.next();
                    cakeList = stringToCake(point);
                }
                String cakePoint = getCakePoint(cakeList);
                String cake = setCake(cakeList);// 给饼分 等级
                //按照等级 给玩家分奖励
                if (isChamMax(cake)) { //大状元 获得所有奖品 结束游戏
                    resultChamMax(result, player.getName(), player.getIndex());
                    player.setPrize("所有的大奖。");
                    System.out.println(player.getIndex() + "号玩家：" + player.getName() + " 摇中了 " + cakePoint + "[" + cake + "]" + "得到了" + player.getPrize());
                    break loop;
                } else {  //不是大状元
                    String prize = getPrize(awards, cake);
                    player.setPrize(prize);
                    System.out.println(player.getIndex() + "号玩家：" + player.getName() + " 摇中了 " + cakePoint + "[" + cake + "]" + "得到了" + player.getPrize());
                    show(result);//打印结果
                }
            }
            delWinner(players, result);
            if (players.size() == 0) {
                break loop;
            }
            if (awards.size() == 0) {
                break loop;
            }
            num++;
            System.out.println();
        }
    }

    public static ArrayList<Player> setPlayer(int n) throws IOException {
        ArrayList<Player> list = new ArrayList<>();
        Player p0 = new Player();
        p0.setName(readName());
        p0.setPrize(" 无 ");
        p0.setIndex(1);
        list.add(p0);
        for (int i = 1; i < n; i++) {
            String name = readName();
            if (isName(list, name)) {
                i--;
                continue;
            }
            Player p = new Player(name, " 无 ", i + 1);
            list.add(p);
        }
        return list;
    }

    public static String readName() throws IOException {
        //创建集合 保存名字
        ArrayList<String> name = new ArrayList<>();
        // 读文件 创建对象与 name.txt 关联
        BufferedReader br = new BufferedReader(new FileReader(new File("F:\\train2\\name.txt")));
        String line = null;//初始化变量
        //br.readLine 读取到的内容
        while (null != (line = br.readLine())) {
            name.add(line);//保存到集合
        }
        br.close();//关闭流对象
        Random r = new Random();
        int index = r.nextInt(name.size());
        return name.get(index);
    }

    public static boolean isName(ArrayList<Player> list, String name) {
        boolean flag = false;
        for (Player player : list) {
            if (name.equals(player.getName())) {
                flag = true;
            }
        }
        return flag;
    }

    public static ArrayList<Award> setAwards() throws IOException {
        //创建集合
        ArrayList<Award> list = new ArrayList<>();
        //创建 对象 BR
        BufferedReader br = new BufferedReader(new FileReader(new File("F:\\train2\\data.txt")));
        String line = null;//line 接收
        String[] name = new String[6];
        int[] count = new int[6];
        for (int i = 0; i < 7; i++) {
            line = br.readLine();
            if (i > 0) {
                StringBuilder sb = new StringBuilder(line);
                sb.replace(0, 3, "");
                line = sb.toString();
                String[] arr = line.split(",");
                name[i - 1] = arr[0].trim();
                arr[1] = arr[1].trim();
                int num = Integer.parseInt(arr[1]);
                count[i - 1] = num;
            }
        }
        Award a0 = new Award("一秀", name[0], count[0]);
        Award a1 = new Award("二举", name[1], count[1]);
        Award a2 = new Award("四进", name[2], count[2]);
        Award a3 = new Award("三红", name[3], count[3]);
        Award a4 = new Award("对堂", name[4], count[4]);
        Award a5 = new Award("状元", name[5], count[5]);
        list.add(a0);
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        return list;
    }

    public static int isPoint(ArrayList<Cake> list, int n) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (n == list.get(i).getPoint()) {
                index = i;
            }
        }
        return index;
    }

    public static ArrayList<Cake> getCake() {
        ArrayList<Cake> list = new ArrayList<>();
        Random r = new Random();
        int p0 = r.nextInt(6) + 1;
        Cake c0 = new Cake(p0, 1);
        list.add(c0);
        for (int i = 1; i < 6; i++) {
            int p = r.nextInt(6) + 1;
            int index = isPoint(list, p);
            if (index == -1) {
                Cake c = new Cake(p, 1);
                list.add(c);
            } else {
                int count = list.get(index).getCount() + 1;
                list.get(index).setCount(count);
            }
        }
        return list;
    }

    public static int getSum(ArrayList<Cake> list) {
        int sum = 0;
        for (Cake cake : list) {
            sum += cake.getPoint() * cake.getCount();
        }
        return sum;
    }

    public static boolean isDT(ArrayList<Cake> list) {
        boolean flag = true;
        for (Cake cake : list) {
            if (cake.getCount() != 1) {
                flag = false;
            }
        }
        return flag;
    }

    public static String setCake(ArrayList<Cake> list) {
        int sum = getSum(list);
        for (Cake cake : list) {
            if (cake.getCount() == 6 && cake.getPoint() == 4) {
                return "六点红";
            } else if (cake.getCount() == 6 && cake.getPoint() == 1) {
                return "遍地锦";
            } else if (cake.getCount() == 6 && cake.getPoint() != 1 && cake.getPoint() != 4) {
                return "遍地黑";
            } else if (cake.getCount() == 5 && cake.getPoint() == 4) {
                return "五红";
            } else if (cake.getCount() == 5 && cake.getPoint() != 4) {
                return "五子登科";
            } else if (cake.getCount() == 4 && cake.getPoint() == 4) {
                if (sum == 18) {
                    return "金花";
                } else {
                    return "四点红";
                }
            } else if (cake.getCount() == 4 && cake.getPoint() != 4) {
                return "四进";
            } else if (cake.getCount() == 3 && cake.getPoint() == 4) {
                return "三红";
            } else if (cake.getCount() == 2 && cake.getPoint() == 4) {
                return "二举";
            } else if (cake.getCount() == 1 && cake.getPoint() == 4) {
                if (isDT(list)) {
                    return "对堂";
                } else {
                    return "一秀";
                }
            }
        }
        return " 无";
    }

    public static boolean isCham(String cake) {
        if ("遍地锦".equals(cake)) {
            return true;
        } else if ("遍地黑".equals(cake)) {
            return true;
        } else if ("五红".equals(cake)) {
            return true;
        } else if ("五子登科".equals(cake)) {
            return true;
        } else if ("四点红".equals(cake)) {
            return true;
        }
        return false;
    }

    public static boolean isChamMax(String cake) {
        if ("金花".equals(cake)) {
            return true;
        } else if ("六点红".equals(cake)) {
            return true;
        }
        return false;
    }

    public static void resultChamMax(ArrayList<Player> result, String maxName, int index) throws IOException {
        ArrayList<Player> result1 = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            result.remove(i);
            i--;
        }
        ArrayList<Award> list = setAwards();
        String prize = "";
        for (Award award : list) {
            prize += award.getName() + "*" + award.getCount() + " ";
        }
        Player player = new Player(maxName, prize, index);
        result1.add(player);
        show(result1);
    }

    public static void show(ArrayList<Player> result) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("menu.txt")));
        String line = null;//初始化
        for (int i = 1; i <= 100; i++) {
            for (Player player : result) {
                if(i==player.getIndex()){line = player.getIndex() + "号玩家：" + player.getName() + "的奖品是" + player.getPrize();
                    //写入文件
                    bw.write(line);
                    bw.newLine();//换行
                }
            }
        }
        bw.close();//关闭流对象
    }

    public static String getCakePoint(ArrayList<Cake> list) {
        String cakePoint = "";
        for (Cake cake : list) {
            for (int i = 0; i < cake.getCount(); i++) {
                cakePoint += cake.getPoint();
            }
        }
        return cakePoint;
    }

    public static String getPrize(ArrayList<Award> list, String cake) {
        String prize = " 无 ";
        String temp = cake;
        boolean flag = false;
        if (isCham(cake)) {
            cake = "状元";
        }
        for (Award award : list) {
            if (cake.equals(award.getCake())) {
                prize = award.getName();
                if (award.getCount() == 1) {
                    flag = true;
                } else {
                    award.setCount(award.getCount() - 1);
                }
            }
        }
        int index = -1;
        if (flag) {
            for (int i = 0; i < list.size(); i++) {
                if (cake.equals(list.get(i).getCake())) {
                    index = i;
                }
            }
            list.remove(index);
        }

        return prize;
    }

    public static void delWinner(ArrayList<Player> players, ArrayList<Player> result) {
        for (int i = 0; i < players.size(); i++) {
            if (!" 无 ".equals(players.get(i).getPrize())) {
                Player p = new Player(players.get(i).getName(), players.get(i).getPrize(), players.get(i).getIndex());
                result.add(p);
                players.remove(i);
                i--;
            }
        }
    }

    public static int[] get5Index(ArrayList<Player> list) {
        int[] arr = new int[5];
        Random r = new Random();
        if (list.size() <= 5) {
            for (int i = 0; i < list.size(); i++) {
                arr[i] = list.get(i).getIndex();
            }
            return arr;
        }
        for (int i = 0; i < 5; i++) {
            int index = r.nextInt(100);
            if (!isEqual(list, index)) {
                i--;
                continue;
            }
            if (i == 0) {
                arr[i] = index;
            } else {
                if (isEqual(arr, index)) {
                    i--;
                    continue;
                } else {
                    arr[i] = index;
                }
            }
        }
        return arr;
    }

    public static boolean isEqual(int[] arr, int n) {
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            if (n == arr[i]) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean isEqual(ArrayList<Player> list, int index) {
        boolean flag = false;
        for (Player player : list) {
            if (index == player.getIndex()) {
                flag = true;
            }
        }
        return flag;
    }

    public static ArrayList<Cake> stringToCake(String point) {
        ArrayList<Cake> list = new ArrayList<>();
        char c0 = point.charAt(0);
        int p0 = c0 - '0';
        Cake cake = new Cake(p0, 1);
        list.add(cake);
        for (int i = 1; i < point.length(); i++) {
            char c = point.charAt(i);
            int p = c - '0';
            int index = isPoint(list,p);
            if(index==-1){
                Cake cake1 = new Cake(p,1);
                list.add(cake1);
            }else {
                list.get(index).setCount(list.get(index).getCount()+1);
            }
        }
        return list;
    }
    public static void printArray(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]!=0){
                System.out.print(arr[i]+" ");
            }
        }
        System.out.println();
    }
}

