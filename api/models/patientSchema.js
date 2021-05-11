const mongoose = require('mongoose');


const patientSchema = mongoose.Schema({
    _id: mongoose.Types.ObjectId,
    name: String,
    phone: String,
    area: String,
    address: String,
    location: pointSchema,
});

module.exports = mongoose.model('Patient', patientSchema);