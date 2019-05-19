package com.thoughtmechanix.assets.controllers;

import com.thoughtmechanix.assets.model.Asset;
import com.thoughtmechanix.assets.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/companys/{companyId}/assets")
public class AssetServiceController {

    @Autowired
    AssetService assetService;

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public ResponseEntity<List<Asset>> getAssets(@PathVariable("companyId") String companyId){
        List<Asset> assets = assetService.getAssetsByCompany(companyId);
        return ResponseEntity.ok(assets);
    }

    @RequestMapping(value= "/{assetId}/{clientType}", method = RequestMethod.GET)
    public ResponseEntity<Asset> getAsset(@PathVariable("companyId") String companyId,
                                          @PathVariable("assetId") String assetId,
                                          @PathVariable("clientType") String clientType){

        Asset asset = assetService.getAsset(companyId, assetId, clientType);
        return ResponseEntity.ok(asset);
    }

    @RequestMapping(value = "/" , method = RequestMethod.POST)
    public ResponseEntity<String> createAsset(@RequestBody Asset asset, @PathVariable("companyId") String companyId){
        assetService.createAsset(asset);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "http://assets-service:8080/v1/" + asset.getCompanyId() + "/assets/" +
                    asset.getAssetId());
    }

    @RequestMapping(value  = "/{assetId}", method = RequestMethod.PUT)
    public ResponseEntity<Asset> updateAsset(@RequestBody Asset assetNew, @PathVariable("companyId") String companyId,
                                      @PathVariable("assetId") String assetId){

        Asset asset = assetService.getAsset(companyId, assetId, "discovery");

        if(asset == null){
            assetService.createAsset(assetNew);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            asset = assetService.updateAsset(asset, assetNew);
        }
         return ResponseEntity.status(HttpStatus.FOUND)
                 .body(asset);
    }


    @RequestMapping(value = "/{assetId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAsset(@PathVariable("companyId") String companyId,
                                      @PathVariable("assetId") String assetId){

        Asset asset = assetService.getAsset(companyId, assetId, "rest");

        if(asset == null){
            return ResponseEntity.notFound().build();
        }

        assetService.deleteAsset(asset);

        return ResponseEntity.status(204).build();
    }
}
