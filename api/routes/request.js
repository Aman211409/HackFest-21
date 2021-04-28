const express = require('express');
const router  = express.Router();
const mongoose = require('mongoose');

const Provider = require('../models/provider');
const Request = require('../models/request');
const pointSchema = require('../models/point');

var kmToRadian = function(km){
    var earthRadiusInKm = 6378;
    return km / earthRadiusInKm;
};

router.post('/patient', (req, res, next) => {
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
            code: 200,
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

router.post('/:essentials_id', (req, res, next) => {
    
    const date = new Date();

    const request = new Request({
        _id: mongoose.Types.ObjectId(),
        essentials_id: req.params.essentials_id,
        date: date,
        patient_id: req.body.patient_id,
        patient_name: req.body.patientName,
        patient_phone: req.body.patientPhone,
        area: req.body.area,
        location: {
            type: pointSchema,
            coordinates: req.body.coordinates
        },
        providers: req.body.providers,
        completed: false
    });
    
    request.save()
    .then(result => {
        return res.status(200).json({
            code: 200,
            message: "Added request successfully"
        })
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});

router.get('/provider/:provider_id', (req, res, next) => {
    const providerId = req.params.provider_id;
    Request.find({
        completed: false,
        providers:{
            provider_id:providerId
        }
    })
    .then(result => {
        
        var arr = [];
        for(i=0; i<result.length; i++){
            const providersArray = result[i].providers.filter(item => item.provider_id == providerId);
            const isAllowed = providersArray[0].approved;

            var performa = {
                location: result[i].location.coordinates,
                area: result[i].area,
                name: result[i].patient_name,
                phone: result[i].patient_phone,
                address: (isAllowed?result[i].patient_address:"Not available"),
                essentials_id: result[i].essentials_id,
                date: result[i].date
            };
            arr.push(performa);
        }
        
        return res.status(200).json({
            code: 200,
            message: "Fetched requests successfully",
            requests: arr
        });
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});

router.get('/approval/:request_id/:provider_id', (req, res, next) => {
    const providerId = req.params.provider_id;
    const requestId = req.params.request_id;

    Request.findOne({
        _id: requestId
    }).exec()
    .then(result => {        
        const original = result.providers;
        var vals = originals.filter(item => item.provider_id !== providerId);
        const ownEntry = original.filter(item => item.provider_id == providerId);
        ownEntry[0].sought_approval = true;
        vals.push(ownEntry[0]);
        vals.reverse();

        Request.updateOne(
        {
            _id: requestId
        },
        {
            $set:{
                providers: vals
            }
        }).exec()
        .then(result => {
            return res.status(200).json({
                code: 200,
                message: "Sought approval successfully",
            });
        })
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});

router.post('/share-address/:request_id/:provider_id', (req, res, next) => {
    const providerId = req.params.provider_id;
    const requestId = req.params.request_id;
    const patientAddress = req.body.address;

    Request.findOne({
        _id: requestId
    }).exec()
    .then(result => {        
        const original = result.providers;
        var vals = originals.filter(item => item.provider_id !== providerId);
        const ownEntry = original.filter(item => item.provider_id == providerId);
        ownEntry[0].approved = true;
        vals.push(ownEntry[0]);
        vals.reverse();

        Request.updateOne(
        {
            _id: requestId
        },
        {
            $set:{
                providers: vals,
                patient_address: patientAddress
            }
        }).exec()
        .then(result => {
            return res.status(200).json({
                code: 200,
                message: "Approved address request successfully",
            });
        })
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});

router.patch('/:request_id', (req, res, next) => {
    
    const requestId = req.params.request_id;
    Request.updateOne(
        {
            _id: requestId
        },
        {
            $set:{
                completed: true
            }
        }
    ).exec()
    .then(result => {
        return res.status(200).json({
            code: 200,
            message: "Marked as completed successfully"
        })
    }).catch(err => {
        console.log(err);
        return res.status(500).json({
            code: 500,
            error: err
        });
    });
});

router.get('/patient/:patient_id', (req, res, next) => {
    const patientId = req.params.patient_id;
    Request.find({
        //completed: false,
        patient_id: patientId
    })
    .then(result => {
        
        var arr = [];
        for(i=0; i<result.length; i++){
            var performa = {
                area: result[i].area,
                providers: result[i].providers,
                essentials_id: result[i].essentials_id,
                date: result[i].date,
                completed: result[i].completed
            };
            arr.push(performa);
        }
        
        return res.status(200).json({
            code: 200,
            message: "Fetched requests successfully",
            requests: arr
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