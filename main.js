$('document').ready(function(){
  app.init();
})

var app = {
  init: function(){
    app.styling();
    app.events();
  },
  styling: function(){

  },
  events: function(){

  },
  create: function(stuff){
    $.ajax({
      url: app.url,
      data: stuff,
      method: "POST",
      success: function(data){
        console.log("X gonna give it to ya::creating", data);
      },
      error: function(err) {
        console.log('dang son',err)
      }
    })
  },
  read: function(){
    $.ajax({
      url: app.url,
      method: "GET",
      success: function(data){
        console.log("X gonna give it to ya::reading", data);
      },
      error: function(err) {
        console.log('dang son',err)
      }
    })
  },
  update: function(stuff){
    $.ajax({
      url: app.url + "/" + stuff._id,
      data: stuff,
      method: "PUT",
      success: function(data){
        console.log("update baby", data);
      },
      error: function(err){
        console.error("dang son!", err)
      }
    })
  },
  delete: function(stuffId){
    var deleteUrl = app.url + "/" + stuffId;
    $.ajax({
      url: deleteUrl,
      method: "DELETE",
      success: function(data){
        console.log("WE DELETED SOMETHING", data);
        app.read();
      },
      error: function(err){
        console.error("dang son!",err);
      }
    })
  },
  template: function(){

  }
}
