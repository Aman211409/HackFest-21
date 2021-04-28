const mongoose = require('mongoose');
const pointSchema = require('./point');

const providerSchema = mongoose.Schema({
    _id: mongoose.Types.ObjectId,
    name: String,
    phone: String,
    area: String,
    location: pointSchema,
    essentials:[Number]
});

module.exports = mongoose.model('Provider', providerSchema);