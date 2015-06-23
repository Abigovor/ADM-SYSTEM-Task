import DB.DaoImpl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Single on 23.06.2015.
 */
public class Download {

    private static volatile Download instance;
    private String startTime, endTime;
    private boolean isInsert;

    private String[] users;


    public Download() {

    }


    public void downloading(String[] users, String startTime, String endTime) {
        this.users = users;

        this.startTime = startTime;
        this.endTime = endTime;


        if (users.length < 1) return;

        Date endTimet = getDate(endTime);
        Date currentTime = new Date();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Update(), 60 * 1000, 60 * 1000);

        insertUsers();

        int t;
        System.out.println(t = currentTime.compareTo(endTimet));
        while ((currentTime.compareTo(endTimet)) < 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime = new Date();
        }

        timer.cancel();
        System.out.println("end");
    }

    private void insertUsers() {
        for (String user : users) {
            try {
                isInsert = DaoImpl.getInstance().insert(user, startTime, endTime, user.getBytes());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Date getDate(String parseDateString) {
        Date parseDate = null;
        try {
            parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parseDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDate;
    }


    class Update extends TimerTask {
        Update() {

        }

        @Override
        public void run() {
            for (String userName : users) {
                updateData(userName);
            }

        }

        public void updateData(String userName) {
            System.out.println("update");
            try {
                DaoImpl.getInstance().update(userName, getRandomByte(userName));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public byte[] getRandomByte(String name) {
            Random random = new Random();
            int value = random.nextInt(50);
            name += name + String.valueOf(value);
            return name.getBytes();
        }
    }
}
