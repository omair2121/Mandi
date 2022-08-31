//package com.secrets.formers.ui;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.TextView;
//import com.secrets.formers.R;
//import java.util.ArrayList;
//
//public class ObjectAdapter extends android.widget.BaseAdapter implements android.widget.Filterable {
//
//    private Context context;
//    private ArrayList<Object> originalList;
//    private ArrayList<Object> suggestions = new java.util.ArrayList<>();
//    private Filter filter = new CustomFilter();
//
//    /**
//     * @param context      Context
//     * @param originalList Original list used to compare in constraints.
//     */
//    public ObjectAdapter(Context context, ArrayList<Object> originalList) {
//        this.context = context;
//        this.originalList = originalList;
//    }
//
//    @Override
//    public int getCount() {
//        return suggestions.size(); // Return the size of the suggestions list.
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return suggestions.get(position).getCountryName();
//    }
//
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    /**
//     * This is where you inflate the layout and also where you set what you want to display.
//     * Here we also implement a View Holder in order to recycle the views.
//     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        ViewHolder holder;
//
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.item_autocomplete,
//                    parent,
//                    false);
//            holder = new ViewHolder();
//            holder.autoText = (TextView) convertView.findViewById(R.id.autoText);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.autoText.setText(suggestions.get(position).getCountryName());
//
//        return convertView;
//    }
//
//
//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    private static class ViewHolder {
//        TextView autoText;
//    }
//
//    /**
//     * Our Custom Filter Class.
//     */
//    private class CustomFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            suggestions.clear();
//
//            if (originalList != null && constraint != null) { // Check if the Original List and Constraint aren't null.
//                for (int i = 0; i < originalList.size(); i++) {
//                    if (originalList.get(i).getCountryName().toLowerCase().contains(constraint)) { // Compare item in original list if it contains constraints.
//                        suggestions.add(originalList.get(i)); // If TRUE add item in Suggestions.
//                    }
//                }
//            }
//            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
//            results.values = suggestions;
//            results.count = suggestions.size();
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            if (results.count > 0) {
//                notifyDataSetChanged();
//            } else {
//                notifyDataSetInvalidated();
//            }
//        }
//    }
//}