package com.example.yelpbusinessexample.data.repository;

import com.example.yelpbusinessexample.data.model.Businesses;

import java.util.HashMap;
import java.util.List;

public interface BusinessRepository {

   interface LoadBusinessesCallback {
      void onBusinessesLoaded(List<Businesses> businesses,int total);
      void onDataNotAvailable();
      void onError(boolean isCancel);
   }

   void cancelCall();
   void getBusinesses(HashMap<String, String> queryMap);
}
