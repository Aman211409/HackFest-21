const mongoose = require('mongoose');


const communitypostSchema = mongoose.Schema({
    _id: mongoose.Types.ObjectId,
    name: String,
    phone: String,
    area: String,
    location: pointSchema,
    details:String,
    type: String 
    
});

module.exports = mongoose.model('CommunityPost', communityPostSchema);