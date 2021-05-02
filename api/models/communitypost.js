const mongoose = require('mongoose');
const pointSchema = require('./point');

const communitypostSchema = mongoose.Schema({
    _id: mongoose.Types.ObjectId,
    name: String,
    phone: String,
    area: String,
    location: pointSchema,
    itemName: String,
    details:String,
    type: Number
});

module.exports = mongoose.model('CommunityPost', communitypostSchema);