const mongoose = require('mongoose');


const communitypostSchema = mongoose.Schema({
    _id: mongoose.Types.ObjectId,
    person_id: mongoose.Types.ObjectId,
    name: String,
    phone: String,
    area: String,
    details:String,
    location: pointSchema,
    type: Number,
    date: Date
});

module.exports = mongoose.model('CommunityPost', communityPostSchema);