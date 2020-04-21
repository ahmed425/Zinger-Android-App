package com.food.ordering.zinger.ui.home

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.food.ordering.zinger.R
import com.food.ordering.zinger.data.model.ShopConfigurationModel
import com.food.ordering.zinger.databinding.ItemShopBinding
import com.food.ordering.zinger.ui.home.ShopAdapter.ShopViewHolder
import com.squareup.picasso.Picasso


class ShopAdapter(private val context: Context, private val shopList: List<ShopConfigurationModel>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ShopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ShopViewHolder {
        val binding: ItemShopBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_shop, parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(shopList[position], holder.adapterPosition, listener)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopViewHolder(var binding: ItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: ShopConfigurationModel, position: Int, listener: OnItemClickListener) {
            Picasso.get().load(shop.shopModel.photoUrl).placeholder(R.drawable.ic_shop).into(binding.imageShop)
            binding.textShopName.text = shop.shopModel.name
            if(shop.configurationModel.isOrderTaken==1){
                binding.textShopName.setTextColor(ContextCompat.getColor(binding.layoutRoot.context,android.R.color.black))
                binding.textShopDesc.setTextColor(ContextCompat.getColor(binding.layoutRoot.context,android.R.color.tab_indicator_text))
                binding.textShopRating.setTextColor(ContextCompat.getColor(binding.layoutRoot.context,android.R.color.tab_indicator_text))
                binding.textShopRating.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_star, 0, 0, 0)
                binding.imageShop.clearColorFilter()
                if(shop.configurationModel.isDeliveryAvailable==1){
                    //binding.textShopDesc.text = "Closes at "+shop.shopModel.closingTime.substring(0,5)
                    binding.textShopDesc.text = "Open now"
                }else{
                    //binding.textShopDesc.text = "Closes at "+shop.shopModel.closingTime.substring(0,5)+" (Delivery not available)"
                    binding.textShopDesc.text = "Open now (Delivery not available)"
                }
            }else{
                binding.textShopDesc.text = "Opens at "+shop.shopModel.openingTime.substring(0,5)
                binding.textShopName.setTextColor(ContextCompat.getColor(binding.layoutRoot.context,R.color.disabledColor))
                binding.textShopDesc.setTextColor(ContextCompat.getColor(binding.layoutRoot.context,R.color.disabledColor))
                binding.textShopRating.setTextColor(ContextCompat.getColor(binding.layoutRoot.context,R.color.disabledColor))
                binding.textShopRating.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_star_disabled, 0, 0, 0)
                val colorMatrix = ColorMatrix()
                colorMatrix.setSaturation(0f)
                val filter = ColorMatrixColorFilter(colorMatrix)
                binding.imageShop.colorFilter = filter
            }
            if(shop.ratingModel.rating==0.0){
                binding.textShopRating.text = "No ratings yet"
            }else {
                binding.textShopRating.text = shop.ratingModel.rating.toString()+" ("+shop.ratingModel.userCount+")"
            }
            binding.layoutRoot.setOnClickListener { listener.onItemClick(shop, position) }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: ShopConfigurationModel, position: Int)
    }

}