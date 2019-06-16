package pl.atlantischi.wechatmoments.data.model

class Tweet {

    var content: String? = null
    var images: Array<String?>? = null
    var sender: Sender? = null
    var error: String? = null //unknown error
    var comments: Array<Comment?>? = null

    class Sender {
        var username: String? = null
        var nick: String? = null
        var avatar: String? = null
    }

    class Comment {
        var content: String? = null
        var sender: Sender? = null
    }

}