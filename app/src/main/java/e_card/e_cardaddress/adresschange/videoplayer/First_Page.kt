package e_card.e_cardaddress.adresschange.videoplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import e_card.e_cardaddress.adresschange.videoplayer.databinding.ActivityFirstPageBinding

class First_Page : AppCompatActivity() {

    lateinit var binding: ActivityFirstPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView (binding.root)

        val Video_Player_btn = findViewById(R.id.Video_Player_btn) as Button

        Video_Player_btn.setOnClickListener {
            val i = Intent(this, Second_Page::class.java)
            startActivity(i)
        }
    }
}