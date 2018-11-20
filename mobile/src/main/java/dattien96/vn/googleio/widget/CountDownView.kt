package dattien96.vn.googleio.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.postDelayed
import com.airbnb.lottie.LottieAnimationView
import dattien96.vn.googleio.R
import dattien96.vn.shared.util.TimeUtils
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CountDownView constructor(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private val root: View = LayoutInflater.from(context).inflate(R.layout.countdown, this, true)
    private var days1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_days_1) }
    private var days2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_days_2) }
    private var hours1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_hours_1) }
    private var hours2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_hours_2) }
    private var mins1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_mins_1) }
    private var mins2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_mins_2) }
    private var secs1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_secs_1) }
    private var secs2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_secs_2) }

    private val updateTime: Runnable = object : Runnable {

        private val conferenceStart = TimeUtils.startDayPlusHour()

        override fun run() {
            // lấy ra tổng time từ now đến ngày start
            var timeUntilConf = Duration.between(ZonedDateTime.now(), conferenceStart)

            if (timeUntilConf.isNegative) {
                // stop the countdown once conf starts
                return
            }

            //convert thành số ngày và set value cho 2 var day
            // khi set value, delegate after fun sẽ auto chạy và gán animation
            val days = timeUntilConf.toDays()
            days1 = (days / 10).toInt()
            days2 = (days % 10).toInt()
            timeUntilConf = timeUntilConf.minusDays(days) //sau khi đã vẽ ngày, thì lấy tổng time - ngày = số giờ

            //tương tự vẽ view giờ, lầy tổng time - số giờ còn dư phút
            val hours = timeUntilConf.toHours()
            hours1 = (hours / 10).toInt()
            hours2 = (hours % 10).toInt()
            timeUntilConf = timeUntilConf.minusHours(hours)

            //vẽ phút và trừ phút để còn lại số giây
            val mins = timeUntilConf.toMinutes()
            mins1 = (mins / 10).toInt()
            mins2 = (mins % 10).toInt()
            timeUntilConf = timeUntilConf.minusMinutes(mins)

            //vẽ giây
            val secs = timeUntilConf.seconds
            secs1 = (secs / 10).toInt()
            secs2 = (secs % 10).toInt()

            handler?.postDelayed(this, 1_000L) // Run self every second
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        handler?.post(updateTime)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler?.removeCallbacks(updateTime)
    }
}

/**
 * Class này kế thừa [ObservableProperty] là con của [ReadWriteProperty]
 * Tương tự case bên SharedPref. Do ta muốn apply cùng 1 kiểu logic cho các var. Ta sẽ dùng property
 * delegation cho nó.
 * Kế thừa class này sẽ vừa có get set của [ReadWriteProperty] và có callback mỗi khi fun set được thực thi
 * là [beforeChange] và [afterChange]
 *
 * [beforeChange] là callback xảy ra trc khi ta gán lại value mới cho var delegate. Nếu fun này return true
 * thì new value mới đc apply, nếu k nó vẫn giữ value cũ. Cái này ta không override, mặc định sẽ return true
 *
 * Trong case này ta chỉ override fun after.
 *
 * Flow: view này ta sẽ hiển thị ngày giờ phút giây. Mỗi loại đều dạng 2 chữ số. Do mỗi chữ số đều
 * có thể thay đổi khi đếm lùi. Nên ta sẽ apply delegation như sau:
 * Cứ mỗi khi 1 chữ giảm 1 đơn vị, tức là nó changeValue, fun afterChange sẽ đc chạy. Nó sẽ dựa vào
 * value hiện tại của mấy biến trên để get ra anim json. Vì mấy file anim có dạng là 0.json, 1.json,..
 *
 * Dùng delegation ta có thể áp toàn bộ logic trong fun after cho 6 biến khi mà value của mỗi biến thay đổi
 * Ta không cần viết lại logic get set cho từng biến
 */
private class AnimateDigitDelegate(
    private val viewProvider: () -> LottieAnimationView
) : ObservableProperty<Int>(-1) {
    override fun afterChange(property: KProperty<*>, oldValue: Int, newValue: Int) {
        //value 1 số phải 0..9
        if (newValue < 0 || newValue > 9) return

        //do ta vẫn để fun before default return true. nên value mới luôn đc apply kể cả có trùng value
        //nên ở đây phải check
        if (oldValue != newValue) {
            val view = viewProvider()
            if (oldValue != -1) {
                // Animate out the prev digit i.e play the second half of it's comp
                view.setAnimation("anim/$oldValue.json",
                    LottieAnimationView.CacheStrategy.Strong
                )
                view.setMinAndMaxProgress(0.5f, 1f)
                // Some issues scheduling & playing 2 * 500ms comps every 1s. Speed up the
                // outward anim slightly to give us some headroom ¯\_(ツ)_/¯
                view.speed = 1.1f
                view.playAnimation()

                view.postDelayed(500L) {
                    view.setAnimation("anim/$newValue.json",
                        LottieAnimationView.CacheStrategy.Strong
                    )
                    view.setMinAndMaxProgress(0f, 0.5f)
                    view.speed = 1f
                    view.playAnimation()
                }
            } else {
                // Initial show, just animate in the desired digit
                view.setAnimation("anim/$newValue.json",
                    LottieAnimationView.CacheStrategy.Strong
                )
                view.setMinAndMaxProgress(0f, 0.5f)
                view.playAnimation()
            }
        }
    }
}
