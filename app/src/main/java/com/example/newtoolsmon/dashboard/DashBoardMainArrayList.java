package com.example.newtoolsmon.dashboard;

import com.example.newtoolsmon.R;

import java.io.Serializable;
import java.util.ArrayList;

public class DashBoardMainArrayList implements Serializable {

    private String Title;
    private String Location;
    private int JobType;

    public static ArrayList<DashBoardMainArrayList> getData() {
        ArrayList<DashBoardMainArrayList> dashBoardMainArrayLists = new ArrayList<>();

        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Analog Clock", R.drawable.clock_two ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Digital Clock", R.drawable.digital_clock ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Torch", R.drawable.tourch ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Color Torch", R.drawable.color_torch ));

        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Mirror", R.drawable.mirror ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("QR Scanner", R.drawable.qr_code ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Location", R.drawable.my_location ));

        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Stopwatch", R.drawable.timer ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Whatsapp web", R.drawable.whatsapp_direct_chat ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Direct Chat", R.drawable.whatsapp_web ));


      //  dashBoardMainArrayLists.add(new DashBoardMainArrayList("Notification log", R.drawable.notification_logger ));


        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Teleweb", R.drawable.teleweb ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Stylish Text", R.drawable.style_text ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Generate QR", R.drawable.generate_qr ));

        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Age Calculator", R.drawable.age_calculator ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Emoji Text", R.drawable.cool ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Color Picker", R.drawable.color ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Password Generator", R.drawable.password_generator ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Text Scanner", R.drawable.text_scanner ));
      //  dashBoardMainArrayLists.add(new DashBoardMainArrayList("Fancy Text", R.drawable.question_mart ));
    //    dashBoardMainArrayLists.add(new DashBoardMainArrayList("Random No Generator", R.drawable.question_mart ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Hash Generator", R.drawable.hash_generator ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("BMI calculator", R.drawable.bmi ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Memory Sanitizer", R.drawable.memory ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Black Board", R.drawable.blackboard ));
     //   dashBoardMainArrayLists.add(new DashBoardMainArrayList("Text Encrypter", R.drawable.question_mart ));
        dashBoardMainArrayLists.add(new DashBoardMainArrayList("Converter", R.drawable.converter ));


        return dashBoardMainArrayLists;
    }







    public DashBoardMainArrayList(String Title, int JobType )
    {
        this.Title = Title;
        this.Location = Location;
        this.JobType = JobType;

    }



    public String getTitle()
    {
        return Title;
    }

    public String getLocation()
    {
        return Location;
    }

    public int getJobType()
    {
        return JobType;
    }


}

