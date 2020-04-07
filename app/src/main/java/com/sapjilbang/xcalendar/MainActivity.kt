package com.sapjilbang.xcalendar

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    //private lateinit var viewCalendar: RecyclerView
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var calendarListAdapter: CalendarListAdapter

    private var mCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarListAdapter = CalendarListAdapter(this)
        layoutManager = GridLayoutManager(this, 7)

        viewCalendar.adapter = calendarListAdapter
        viewCalendar.layoutManager = layoutManager

        mCalendar.set(Calendar.DATE, 1)
        tvCurrentMonth.text = (mCalendar.get(Calendar.MONTH) + 1).toString() + "월"

        tvPrevMonth.setOnClickListener {
            mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) - 1)
            tvCurrentMonth.text = (mCalendar.get(Calendar.MONTH) + 1).toString() + "월"
            calendarListAdapter.notifyDataSetChanged()
        }
        tvNextMonth.setOnClickListener {
            mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) + 1)
            tvCurrentMonth.text = (mCalendar.get(Calendar.MONTH) + 1).toString() + "월"
            calendarListAdapter.notifyDataSetChanged()
        }
    }

    inner class CalendarListAdapter(val context: Context) : RecyclerView.Adapter<CalendarListAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(parent)
        }

        override fun getItemCount(): Int {
            return 7 * (1 + 6)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {

            val startDay = mCalendar.get(Calendar.DAY_OF_WEEK) // 월 시작 요일
            val lastDay = mCalendar.getActualMaximum(Calendar.DATE) // 월 마지막 날짜

            when(position) {
                0 -> {
                    holder.tvCalendarDay.text = "일"
                    holder.tvCalendarDay.setTextColor(Color.RED)
                }
                1 -> holder.tvCalendarDay.text = "월"
                2 -> holder.tvCalendarDay.text = "화"
                3 -> holder.tvCalendarDay.text = "수"
                4 -> holder.tvCalendarDay.text = "목"
                5 -> holder.tvCalendarDay.text = "금"
                6 -> holder.tvCalendarDay.text = "토"
                else -> {
                    if(position < startDay + 7 - 1 || position > lastDay + 7 + 2) {
                        holder.tvCalendarDay.text = ""
                    } else {
                        holder.tvCalendarDay.text = (position - 7 - startDay + 2).toString()
                    }
                }
            }
        }

        inner class Holder(parent: ViewGroup?) : RecyclerView.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_calendar, parent, false)) {
            val tvCalendarDay: TextView = itemView.findViewById(R.id.tvCalendarDay)
        }
    }
}
