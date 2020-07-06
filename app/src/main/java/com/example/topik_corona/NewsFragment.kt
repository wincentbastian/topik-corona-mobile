package com.example.topik_corona

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.topik_corona.Utils.Constants
import com.example.topik_corona.adapter.BannerAdapter
import com.example.topik_corona.adapter.MyAdapter
import com.example.topik_corona.model.Data
import com.example.topik_corona.model.News

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private val bannerList: MutableList<Data> = mutableListOf()
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var bannerRecyclerView: RecyclerView

    private lateinit var nameNews: TextView


    private val dataList: MutableList<Data> = mutableListOf()
    private lateinit var myAdapter: MyAdapter
    private lateinit var news_recycler_view: RecyclerView
    private lateinit var loadingRecycler: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        activity!!.title = "News"

        val view: View = inflater.inflate(R.layout.fragment_news, container, false)
        loadingRecycler = view.findViewById(R.id.loading_recycler)
        nameNews = view.findViewById(R.id.news_user_name_page)

        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("user", Context.MODE_PRIVATE)

        val userName = sharedPreferences.getString("name", "")!!

        nameNews.text = "Berita untuk " + userName

        //setup adapter
        myAdapter = MyAdapter(dataList)

        //setup recyclerview
        news_recycler_view = view.findViewById(R.id.news_recycler_view)
        news_recycler_view.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        news_recycler_view.adapter = myAdapter

        bannerAdapter = BannerAdapter(bannerList)

        //setup recyclerview
        bannerRecyclerView = view.findViewById(R.id.bannernews_recycler_view)
        bannerRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        bannerRecyclerView.adapter = bannerAdapter


        //connection
        AndroidNetworking.initialize(context)
        AndroidNetworking.get( Constants.BASE_URL +"showNews.php")
                .build()
                .getAsObject(News::class.java, object : ParsedRequestListener<News>{
                    override fun onResponse(response: News) {
                        dataList.addAll(response.data)
                        myAdapter.notifyDataSetChanged()
                        loadingRecycler.visibility = View.GONE

                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(context, "Recycler View Error", Toast.LENGTH_LONG).show()
                    }

                })

        AndroidNetworking.initialize(context)
        AndroidNetworking.get(Constants.BASE_URL + "showPopulerNews.php")
                .build()
                .getAsObject(News::class.java, object : ParsedRequestListener<News>{
                    override fun onResponse(response: News) {

                        bannerList.addAll(response.data)
                        bannerAdapter.notifyDataSetChanged()
                        loadingRecycler.visibility = View.GONE

                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(context, "Recycler View Error", Toast.LENGTH_LONG).show()
                    }

                })









        return view
    }

}