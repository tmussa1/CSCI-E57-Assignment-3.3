package com.thoughtmechanix.assets.repository;

import com.thoughtmechanix.assets.model.Asset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends CrudRepository<Asset, String> {

    //I did contains because if the user had a typo, it will still find it
    public List<Asset>  findAllByCompanyId(String companyId);

    public Asset findByCompanyIdAndAssetId(String companyId, String assetId);


}
