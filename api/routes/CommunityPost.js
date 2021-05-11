const express = require('express');
const router  = express.Router();
const mongoose = require('mongoose');
const CommunityPost = require('../models/communitypost')

module.exports = router;


var kmToRadian = function(km){
    var earthRadiusInKm = 6378;
    return km / earthRadiusInKm;
};

//Get community Post Nearby specifically
router.post('/:communityPostType/nearby', (req, res, next) => {
    const latitude = req.body.latitude;
    const longitude = req.body.longitude;
    const type = req.params.communityPostType
  
    CommunityPost.find({
        location:{
            $geoWithin : {
                $centerSphere : [[longitude, latitude], kmToRadian(200) ]
            }
        },
        type: type
    }).exec()
    .then(result => {
        return res.status(200).json({
            code: 200,
            message: "Fetched Posts SuccessFully.",
            posts:result
        });
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});


//Create new request
router.post('/:communityPostType', (req, res, next) => {
    
    const date = new Date();

    const communitypost = new CommunityPost({
        _id: mongoose.Types.ObjectId(),
        person_id: req.body.person_id,
        name: req.body.name,
        phone: req.body.phone,
        area: req.body.area,
        details: req.body.details,
        item: req.body.item,
        location: {
            type: "Point",
            coordinates: req.body.coordinates
        },
        type: req.params.communityPostType,
        date: date,
    });
    
    communitypost.save()
    .then(result => {
        return res.status(200).json({
            code: 200,
            message: "Community Post Successfully Added"
        })
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});


/* Delete Community Post */


router.delete('/:communityPostId', (req, res, next) => {
    
    const requestId = req.params.request_id;
    CommunityPost.deleteOne(
        {
            _id: requestId
        },   
    ).exec()
    .then(result => {
        return res.status(200).json({
            code: 200,
            message: "Deleted post succesfully."
        })
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});