package com.example.evcharger;

import com.example.evcharger.DAO.impl.Userimpl;
import com.example.evcharger.vo.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void DBtest(){
                User user = new User();
                user.setName("dddd");
                user.setPassword("nnnnnn");
                user.setPhone("213123");
                user.setSex(1);

                new Userimpl().InsertUser(user);
                System.out.println(2131);


    }
}