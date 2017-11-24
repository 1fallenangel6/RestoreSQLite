package com.example.a15747.restoresqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a15747.restoresqlite.Database.DatabaseHelper;
import com.example.a15747.restoresqlite.Database.DatabaseManager;
import com.example.a15747.restoresqlite.Database.User;
import com.example.a15747.restoresqlite.Database.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // 创建可以容纳3个线程的线程池
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization, should only be done once, preferable inside application class
        DatabaseManager.initializeInstance(new DatabaseHelper(MainActivity.this), MainActivity.this);
        // insert single user
        User user = new User();
        user.setAge(100);
        user.setName("Jon Doe");

        UserDAO dao = new UserDAO();
        fixedThreadPool.execute(new readTask(dao,100));
        fixedThreadPool.execute(new insertTask(generateDummyUserList(100,"Jon Doe"),dao));


    }

    //"Jon Doe" "Hello Ketty"
    private List<User> generateDummyUserList(int itemsCount,String name) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < itemsCount; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("Jon Doe");
            userList.add(user);
        }
        return userList;
    }

    class insertTask implements Runnable {
        List<User> userList;
        UserDAO dao;
        public insertTask(List<User> userList,UserDAO dao){
            this.userList = userList;
            this.dao = dao;
        }
        public void run(){
            for(User us:this.userList) {
                dao.insert(us);
                Log.d("--UserList--","Wtite "+us.getId()+" "+us.getAge()+" "+us.getName());

            }
        }
    }

    class readTask implements Runnable {
        List<User> userList;
        int times;
        UserDAO dao;
        public readTask(UserDAO dao,int times){
            this.dao = dao;
            this.times = times;
        }
        public void run(){
            for(int i=0;i<=times;i++) {
                userList = dao.selectAll();
                for(User x:userList)
                    Log.d("--UserList--","READ "+x.getId()+" "+x.getAge()+" "+x.getName());
            }
        }
    }
}
