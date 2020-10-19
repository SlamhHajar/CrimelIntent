package com.hajarslamah.crimelintent_0



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import java.text.DateFormat

class CrimeListFragment:Fragment() {
    private lateinit var crimeRecyclerView:RecyclerView
    private var adapter:CrimeAdapter?=null

    /////////////////////////////////////ViewModel/////////////////////////////////////////
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)    }
    ///////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
////////////////////////////////////StaticInstance///////////////////////////////
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
    ///////////////////////////////////////OncreateView/////////////////////
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.crime_list_fragment,container,false)
        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

          updateUI()
        return  view
    }

    /////////////////////////////////////////Fun UpdateUI///////////////////
    private fun updateUI() {

         val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }
    /////////////////////////////////////ViewHolder/////////////////
    private  inner class CrimeViewHolder(view:View): ViewHolder(view), View.OnClickListener{
        private lateinit var crime: Crime
        var solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)
        //var contactPolice: ImageButton = itemView.findViewById(R.id.contactPolice)
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(crime: Crime){
            this.crime = crime
//         contactPolice.setOnClickListener {
//             Toast.makeText(context, "Loading to calling......!", Toast.LENGTH_SHORT)
//                 .show()
//         }
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)
                .show()

        }
    }
    /////////////////////////////////////Adapter//////////////////////////////
    private inner class CrimeAdapter(var crimes: List<Crime>)
        : RecyclerView.Adapter<CrimeViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : CrimeViewHolder {
            val crimePoliceView = layoutInflater.inflate(R.layout.list_item_crime_police, parent, false)
            val crimeNormalView = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            if(viewType==R.layout.list_item_crime_police){
                return CrimeViewHolder(crimePoliceView)
            }else {
                return CrimeViewHolder(crimeNormalView)
            }
        }
        override fun getItemCount() = crimes.size
        override fun getItemViewType(position:Int):Int{
            return when(crimes[position].requiresPolice){
                true->R.layout.list_item_crime_police
                false->R.layout.list_item_crime

            }}
        override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }
    }
}

