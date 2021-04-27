const express = require('express');
const router  = express.Router();
const mongoose = require('mongoose');

const Provider = require('../models/provider');

var kmToRadian = function(km){
    var earthRadiusInKm = 6378;
    return km / earthRadiusInKm;
};

router.post('/', (req, res, next) => {
    const latitude = req.body.latitude;
    const longitude = req.body.longitude;

    Provider.find({
        location:{
            $geoWithin : {
                $centerSphere : [[longitude, latitude], kmToRadian(50) ]
            }
        }
    }).exec()
    .then(providers => {
        return res.status(200).json({
            status: 200,
            message: "Fetched providers successfully.",
            providers:providers
        });
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});

module.exports = router;