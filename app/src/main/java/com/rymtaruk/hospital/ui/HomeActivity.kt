package com.rymtaruk.hospital.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.rymtaruk.core.di.util.ViewModelFactory
import com.rymtaruk.hospital.R
import com.rymtaruk.hospital.databinding.ActivityHomeBinding
import com.rymtaruk.hospital.model.AdditionalData
import com.rymtaruk.hospital.model.DailyData
import com.rymtaruk.hospital.ui.search.SearchFragment
import dagger.android.AndroidInjection
import java.text.SimpleDateFormat
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onObserverData()
        initHorizontalChart()

        binding.cvSearch.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val searchFragment = SearchFragment.newInstance(viewModelFactory)
            searchFragment.show(fragmentManager, SearchFragment::class.java.name)
        }
    }

    private fun onObserverData() {
        viewModel.defaultLoading.observeForever {
            binding.pbLoading.visibility = it
        }

        viewModel.showContent.observeForever {
            binding.scrollView.visibility = it
        }

        viewModel.responseCovidData.observeForever {
            binding.llHeader.tvTotalOdp.text = String.format("%,d", it.data.jumlahOdp)
            binding.llHeader.tvTotalPdp.text = String.format("%,d", it.data.jumlahPDP)
            binding.llHeader.tvNegativeSpecimen.text =
                String.format("%,d", it.data.totalSpesimenNegatif)
            binding.llHeader.tvSpecimen.text = String.format("%,d", it.data.totalSpesimen)
            setPieChart(it.update.penambahan)
            selectedDailyData(it.update.harian[0])
            initSpinner(it.update.harian)
        }

        viewModel.defaultError.observeForever {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setPieChart(data: AdditionalData) {
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true)
        val color = typedValue.data
        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(data.jumlahDirawat.toFloat(), "Inpatient"))
        entries.add(PieEntry(data.jumlahSembuh.toFloat(), "Recover"))
        entries.add(PieEntry(data.jumlahPositif.toFloat(), "Positive"))
        entries.add(PieEntry(data.jumlahMeninggal.toFloat(), "Pass Away"))

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 5.0f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.color = Color.BLACK

        val colors: ArrayList<Int> = ArrayList()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        colors.add(color)
        dataSet.colors = colors

        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(13f)
        pieData.setValueTextColor(Color.RED)
        binding.pcUpdate.description.text = "Updated " + data.created
        binding.pcUpdate.setEntryLabelColor(Color.RED)
        binding.pcUpdate.setHoleColor(color)
        binding.pcUpdate.data = pieData
        binding.pcUpdate.highlightValues(null)
        binding.pcUpdate.animate()
        binding.pcUpdate.invalidate()
    }

    @SuppressLint("SimpleDateFormat")
    private fun selectedDailyData(data: DailyData) {

        val barWidth = 10f
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()
        val entries3 = ArrayList<BarEntry>()
        val entries4 = ArrayList<BarEntry>()

        entries1.add(BarEntry(1 * 10f, data.jumlahDirawat.value.toFloat()))
        entries1.add(BarEntry(3 * 10f, data.jumlahDirawatKum.value.toFloat()))
        entries2.add(BarEntry(5 * 10f, data.jumlahSembuh.value.toFloat()))
        entries2.add(BarEntry(7 * 10f, data.jumlahSembuhKum.value.toFloat()))
        entries3.add(BarEntry(9 * 10f, data.jumlahPositif.value.toFloat()))
        entries3.add(BarEntry(11 * 10f, data.jumlahPositifKum.value.toFloat()))
        entries4.add(BarEntry(13 * 10f, data.jumlahMeninggal.value.toFloat()))
        entries4.add(BarEntry(15 * 10f, data.jumlahMeninggalKum.value.toFloat()))

        val set1: BarDataSet
        val set2: BarDataSet
        val set3: BarDataSet
        val set4: BarDataSet
        if (binding.hbcDaily.data != null && binding.hbcDaily.data.dataSetCount > 0) {
            set1 = binding.hbcDaily.data.getDataSetByIndex(0) as BarDataSet
            set1.values = entries1

            set2 = binding.hbcDaily.data.getDataSetByIndex(1) as BarDataSet
            set2.values = entries2

            set3 = binding.hbcDaily.data.getDataSetByIndex(2) as BarDataSet
            set3.values = entries3

            set4 = binding.hbcDaily.data.getDataSetByIndex(3) as BarDataSet
            set4.values = entries4

            setLayoutHorizontalBar(set1, set2, set3, set4)
            binding.hbcDaily.data.notifyDataChanged()
            binding.hbcDaily.notifyDataSetChanged()
            binding.hbcDaily.invalidate()
            binding.hbcDaily.animateY(1500)
        } else {
            set1 = BarDataSet(entries1, "Inpatient")

            set2 = BarDataSet(entries2, "Recover")

            set3 = BarDataSet(entries3, "Positive")

            set4 = BarDataSet(entries4, "Pass Away")

            setLayoutHorizontalBar(set1, set2, set3, set4)

            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)
            dataSets.add(set2)
            dataSets.add(set3)
            dataSets.add(set4)

            val barData = BarData(dataSets)
            barData.setValueTextSize(10f)
            barData.barWidth = barWidth
            binding.hbcDaily.data = barData
        }
    }

    private fun initHorizontalChart() {
        binding.hbcDaily.setDrawBarShadow(false)
        binding.hbcDaily.setDrawValueAboveBar(false)
        binding.hbcDaily.description.isEnabled = false
        binding.hbcDaily.setMaxVisibleValueCount(60)
        binding.hbcDaily.setPinchZoom(true)
        binding.hbcDaily.setDrawGridBackground(false)

        val xl = binding.hbcDaily.xAxis
        xl.position = XAxis.XAxisPosition.BOTTOM
        xl.setDrawAxisLine(true)
        xl.setDrawGridLines(false)
        xl.setDrawLabels(false)
        xl.granularity = 0f

        val yl = binding.hbcDaily.axisLeft
        yl.setDrawAxisLine(true)
        yl.setDrawGridLines(true)
        yl.axisMinimum = 0f

        val yr = binding.hbcDaily.axisRight
        yr.setDrawAxisLine(false)
        yr.setDrawGridLines(false)
        yr.isEnabled = false

        binding.hbcDaily.setFitBars(true)
        binding.hbcDaily.animateY(1500)

        val l: Legend = binding.hbcDaily.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 4f
        l.xEntrySpace = 8f
    }

    private fun setLayoutHorizontalBar(
        set1: BarDataSet,
        set2: BarDataSet,
        set3: BarDataSet,
        set4: BarDataSet
    ) {
        set1.color = Color.GREEN
        set1.valueTextSize = 12f
        set2.color = Color.CYAN
        set2.valueTextSize = 12f
        set3.color = Color.YELLOW
        set3.valueTextSize = 12f
        set4.color = Color.LTGRAY
        set4.valueTextSize = 12f

        set1.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        set2.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        set3.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        set4.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initSpinner(dailies: MutableList<DailyData>) {
        val items = ArrayList<String>()

        for (daily in dailies) {
            val strDate = daily.keyAsString
            var format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
            val date = format.parse(strDate)

            format = SimpleDateFormat("dd MMM yyyy")
            items.add(format.format(date!!))

            val adapters = ArrayAdapter(this, R.layout.spinner_item, items)
            adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spDate.apply {
                adapter = adapters
            }
        }

        binding.spDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedDailyData(dailies[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }
}