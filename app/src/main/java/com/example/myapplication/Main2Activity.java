package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    private static final int REQEUST_CODE_MENU = 101;
    private ProgressDialog progressDialog;
    private String temp;
    ArrayAdapter adapter;
    ListView listview;
    ArrayList<String> List_Hospital = new ArrayList<String>();
    ArrayList<String> done_hospital = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Button btn,btn2,btn5;
        Button btn_search;
        //request2();
/*
        List_Hospital.add("강원병원");
        List_Hospital.add("충청병원");
        List_Hospital.add("대구병원");
        List_Hospital.add("경북병원");
        List_Hospital.add("경남병원");
        List_Hospital.add("강남병원");
        List_Hospital.add("전북병원");
        List_Hospital.add("전남병원");
        List_Hospital.add("제주병원");
*/
        btn = findViewById(R.id.button10);
        btn2 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        final Intent intent = new Intent();


        btn_search = findViewById(R.id.btn_search);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, List_Hospital) ;
         listview = (ListView) findViewById(R.id.listview) ;
         listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 코드 계속 ...

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;
                System.out.println(strText);
                Toast.makeText(Main2Activity.this,strText+"를 선택하였습니다.",Toast.LENGTH_LONG).show();
                btn5.setText("선택한 병원: "+strText);
                // TODO : use strText


            }

        }) ;




        btn_search.setOnClickListener(new View.OnClickListener()
        {
            EditText date;

            @Override
            public void onClick(View v) {
                adapter.clear();
                date = (EditText) findViewById(R.id.date);
                System.out.println("123");
                System.out.println(date.getText().toString());
                temp = date.getText().toString();
                request2();
                request1(date.getText().toString());
                adapter.notifyDataSetChanged();

            }
        });
        //날짜 선택하기 .

        //자기 자신이 종료됨.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,Main3Activity.class);
                startActivityForResult(i,REQEUST_CODE_MENU);
                i.putExtra("name",btn5.getText().toString().replace("선택한 병원: ",""));
                System.out.println("병원선택"+btn5.getText().toString().replace("선택한 병원: ",""));
                setResult(RESULT_OK,i);
                startActivity(i);
            }
        });

    }
    public void request2(){

        final String url = "http://192.168.0.52:8080/selectAll";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            System.out.println("Response is : " + response);
                            JSONArray jsonresponse = null;
                            try {
                                jsonresponse = new JSONArray(response);
                                System.out.println("응답"+ response);
                                for(int i = 0 ; i < jsonresponse.length();i++)
                                {
                                    JSONObject item = jsonresponse.getJSONObject(i);
                                    System.out.println("객체"+item.toString());
                                    System.out.println("객체"+item.get("hospital_name"));
                                    String temp = item.get("hospital_name").toString();
                                    List_Hospital.add(temp);
                                    System.out.println("list_hospital"+temp);

                                    adapter.notifyDataSetChanged();

                                }


                                for(int i = 0 ; i < List_Hospital.size();i++)
                                {
                                    System.out.println("객체3"+ List_Hospital.get(i));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {


                            } catch (Exception ex) {
                                // System.out.println("EXCEPTION IN SUCCESS OF LOGIN REQUEST : " + ex.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        private Dialog progressDialog;

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // progressDialog.dismiss();
                            System.out.println("ERROR IN LOGIN STRING REQUEST : " + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("date1", temp);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
            requestQueue.add(stringRequest);
            progressDialog = new ProgressDialog(Main2Activity.this);
           progressDialog.setMessage("searching");
            progressDialog.show();
        } catch (Exception ex) {
            System.out.println(" Exception : " + ex);}
    }




    public void request1(final String date){

        final String url = "http://192.168.0.52:8080/check";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            System.out.println("Response is : " + response);
                            JSONArray jsonresponse = null;
                            try {
                                jsonresponse = new JSONArray(response);
                                System.out.println("파싱을하자.");
                                System.out.println("사이즈 "+jsonresponse.length());

                                for(int i = 0 ; i < jsonresponse.length();i++)
                                {
                                    JSONObject item = jsonresponse.getJSONObject(i);
                                    System.out.println("객체"+item.toString());
                                    System.out.println("객체"+item.get("hospital_name"));
                                    String temp = item.get("hospital_name").toString();
                                    System.out.println("객체"+temp);
                                    done_hospital.add(temp);
                                }
                                for(int i = 0 ; i < done_hospital.size();i++)
                                {
                                    System.out.println("객체2"+ done_hospital.get(i));
                                    List_Hospital.remove(done_hospital.get(i));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {


                            } catch (Exception ex) {
                               // System.out.println("EXCEPTION IN SUCCESS OF LOGIN REQUEST : " + ex.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        private Dialog progressDialog;

                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // progressDialog.dismiss();
                            System.out.println("ERROR IN LOGIN STRING REQUEST : " + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("date1", temp);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
            requestQueue.add(stringRequest);
            progressDialog = new ProgressDialog(Main2Activity.this);
       //     progressDialog.setMessage("searching");
         //   progressDialog.show();
        } catch (Exception ex) {
            System.out.println(" Exception : " + ex);}
    }

/*
    public void request_select(){

        final String url = "http://172.30.1.58:8080/check";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            System.out.println("Response is : " + response);

                            try {
                                JSONObject jsonLogin = new JSONObject(response);

                            } catch (Exception ex) {
                                // System.out.println("EXCEPTION IN SUCCESS OF LOGIN REQUEST : " + ex.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        private Dialog progressDialog;

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // progressDialog.dismiss();
                            System.out.println("ERROR IN LOGIN STRING REQUEST : " + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("date1", temp);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
            requestQueue.add(stringRequest);
            progressDialog = new ProgressDialog(Main2Activity.this);
            progressDialog.setMessage("searching");
            progressDialog.show();
        } catch (Exception ex) {
            System.out.println(" Exception : " + ex);}
    }

*/

/*
    public void request(String date){
        //url 요청주소 넣는 editText를 받아 url만들기
        String url = "http://172.30.1.58:8080/check";
        HashMap<String, Object> myHashMap1 = new HashMap<String, Object>();
        myHashMap1.put("date1","name");

        JSONObject json1 = new JSONObject();
        try {
            json1.put("date1",date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString = json1.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            System.out.println(json1);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,json1,
                    new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    //System.out.println(response);
                        System.out.println("데이터 전송 성공");
                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(Main2Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);


    }

*/



}
