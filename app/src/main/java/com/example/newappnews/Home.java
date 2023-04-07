package com.example.newappnews;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends RecyclerView.Adapter<Home.ViewHolder> {

    private JSONArray localDataSet;
    int  h, n=99;
static int i=0;
    private Context ct;
    DbHandler db;
    DbHandler2 d2;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private static ImageView img;
        private final TextView sTextView;
        private static Button button;
        private static Button save, Delete;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.Title);
            img = view.findViewById(R.id.imageView4);
            sTextView = view.findViewById(R.id.Description);
            button = view.findViewById(R.id.button);
            save = view.findViewById(R.id.save);
            Delete = view.findViewById(R.id.Delete);

        }

        public TextView getTextView() {
            return textView;
        }

        public static ImageView getImgView() {
            return img;
        }

        public static Button getButton() {
            return button;
        }

        public static Button getSave() {
            return save;
        }

        public static Button getDelete() {
            return Delete;
        }


        public TextView getDesView() {
            return sTextView;
        }
//        public static void visible(Button v,Button g){
//            Log.d("mytag", "visible: 1");
//            v.setVisibility(View.VISIBLE);
//            g.setVisibility(View.GONE);
//            Log.d("mytag", "visible: 2");
//
//        }
    }

    public Home(JSONArray response, Context c, DbHandler d, int g) {
        localDataSet = response;
        ct = c;
        db = d;
        h = g;
        d2=new DbHandler2(ct,"savedIndex",null,1);

    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        try {

            JSONObject array = localDataSet.getJSONObject(position);
            String title = array.getString("title");
            String url = array.getString("url");
         String k = array.getString("publishedAt");
            Log.d("myTag", title);
            viewHolder.getTextView().setText(title );
            String desc = array.getString("description");
            if (desc.length() > 100) {
                viewHolder.getDesView().setText(desc.substring(0, 100));
            } else {
                viewHolder.getDesView().setText(desc);
            }

            String urlToImage = array.getString("urlToImage");
            Glide.with(ct).load(urlToImage).into(ViewHolder.getImgView());
            if (h == 1) {
                ViewHolder.getSave().setVisibility(View.GONE);
                ViewHolder.getDelete().setVisibility(View.VISIBLE);
            }
            Glide.with(ct).load(urlToImage).into(ViewHolder.getImgView());
            if (h == 0) {
                ViewHolder.getDelete().setVisibility(View.GONE);
                ViewHolder.getSave().setVisibility(View.VISIBLE);
            }
            ViewHolder.getSave().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    while(true){
//                        try {
//                            if(db.getData(i)!=null){
//                                i++;
//                            }
//                            else{
//                                break;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    n=i;
                    i=0;
                    int c=0;
                while(i<db.getCount()){
                    try {
                        if(db.getData(i)==null){
                            i++;
                            continue;
                        }
                        else{
                            if(db.getData(i).getString("title").equals(title)){
                                Toast.makeText(ct, "Already saved", Toast.LENGTH_SHORT).show();
                                c=1;
                                break;
                            }
                            i++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }if(c!=1){
                i=db.getCount();
                    db.addData(i, title, desc, urlToImage, url,Integer.toString(i));

                    Toast.makeText(ct, "Saved", Toast.LENGTH_SHORT).show();
                    Log.d("myTag", "onClick: "+i);}
                }
            });
            ViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Log.d("Debug", "exception1 ");
                        MainActivity2.getContext(url);
                        Intent intent = new Intent(ct, MainActivity2.class);
                        Log.d("Debug", "exception 2");
//                        intent.putExtra("url", array.getString("url"));
                        ct.startActivity(intent);
                        Log.d("Debug", "exception 3");
                    } catch (Exception e) {
                        Log.d("Debug", "exception4 ");
                    }

                }
            });
ViewHolder.getDelete().setOnClickListener(new View.OnClickListener() {
    @Override
    public  void onClick(View v) {
//        int k=0;
//      while(k<i){
//          try {
//              if(db.getData(k).getString("title").equals(title)){
//              db.deleteData(k);
//              break;}
//              k++;
//              Toast.makeText(ct, "Reload to get updated", Toast.LENGTH_SHORT).show();
//          } catch (JSONException e) {
//              e.printStackTrace();
//          }
//      }
        db.deleteData(Integer.parseInt(k));
        Toast.makeText(ct, "Reload to get updated", Toast.LENGTH_SHORT).show();

    }
});
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return localDataSet.length();
    }


    public static  int getI() {
        return i;
    }
}