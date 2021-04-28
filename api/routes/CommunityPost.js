const express = require('express');
const router  = express.Router();
const mongoose = require('mongoose');

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
        }
    }).exec()
    .then(result => {

        var posts= [];
        for(i=0; i<result.length; i++){
            if(result[i].type.filter(item => item == type).length !== 0){
                posts.push(result[i]);
            }
        }
        
        return res.status(200).json({
            code: 200,
            message: "Fetched Posts SuccessFully.",
            posts:posts
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
        type: req.params.communityPostType,
        date: date,
        patient_id: req.body.patient_id,
        patient_name: req.body.patientName,
        patient_phone: req.body.patientPhone,
        area: req.body.area,
        location: {
            type: pointSchema,
            coordinates: req.body.coordinates
        },
        completed: false
    });
    
    communitypost.save()
    .then(result => {
        return res.status(200).json({
            code: 200,
            message: "Community Post SuccessFully Added"
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
    Request.deleteOne(
        {
            _id: requestId
        },   
    ).exec()
    .then(result => {
        return res.status(200).json({
            code: 200,
            message: "Deleted Succesfully"
        })
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});


