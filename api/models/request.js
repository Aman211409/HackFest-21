const mongoose = require('mongoose');
const pointSchema = require('./point');

const requestSchema = mongoose.Schema({
    _id: mongoose.Types.ObjectId,
    patient_id: String,
    patient_name: String,
    patient_phone: String,
    patient_address: {
        type:String,
        default: "Not available"
    },
    area: String,
    essentials_id: Number,
    location: pointSchema,
    date: Date,
    providers:[{
        name:String,
        phone: String,
        provider_id: mongoose.Types.ObjectId,
        sought_approval:{
            type: Boolean,
            default: false
        },
        approved:{
            type: Boolean,
            default: false
        }
    }],
    completed:{
        type: Boolean,
        default: false
    }
});

module.exports = mongoose.model('Request', requestSchema);