package com.myacumen.whatsappdirectmsgr.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myacumen.whatsappdirectmsgr.R;
import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        //fancyAboutPage.setCoverTintColor(Color.BLUE);  //Optional
        fancyAboutPage.setCover(R.color.carbon_green_50); //Pass your cover image
        fancyAboutPage.setName("Mujahed Mujju");
        fancyAboutPage.setDescription("Google Certified Associate Android Developer | Android App, Game, Web and Software Developer.");
        fancyAboutPage.setAppIcon(R.drawable.directmsgr); //Pass your app icon image
        fancyAboutPage.setAppName("Direct Messenger Icon Pack");
        fancyAboutPage.setVersionNameAsAppSubTitle("1.2.0");
        fancyAboutPage.setAppDescription("Direct Messenger Icon Pack is an icon pack which follows Google's Material Design language.\n\n" +
                "This icon pack uses the material design color palette given by google. Every icon is handcrafted with attention to the smallest details!\n\n"+
                "A fresh new take on Material Design iconography. Direct Messenger offers unique, creative and vibrant icons. Spice up your phones home-screen by giving it a fresh and unique look with Polycon.");
        fancyAboutPage.addEmailLink("whatsappdirectmsgr@ims.com");     //Add your email id
        fancyAboutPage.addFacebookLink("https://www.facebook.com//");  //Add your facebook address url
        fancyAboutPage.addTwitterLink("https://twitter.com/MujahedMujju1/");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/");
        fancyAboutPage.addGitHubLink("https://github.com/mujahedmuju/");
    }
}