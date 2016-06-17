$('document').ready(function(){
  app.init();
})

var app = {
  user: '',
  init: function(){
    app.styling();
    app.events();
  },
  loginSubmit: function(){
    event.preventDefault();
    app.user = $('input[name=username]').val();
    var $password = $('input[type=password]').val();
    console.log("shit submit", app.user);
    if($password === 'poop') {
      $('.login').fadeOut();
      $('.hoh-main').removeClass("hidden").hide().fadeIn(2000);
    } else {
      $('input[type=password]').val('').attr('placeholder','wrong password!');
    }
  },
  styling: function(){

  },
  events: function(){
    //submit & store username and password on click
    $('button[type=button]').on('click', function(event){
      app.loginSubmit();
    })
    //submit & store username and password on enter
    $('.login').on('keypress click', function(event){
    var keyCode = event.keyCode || event.which;
      if (keyCode === 13) {
        app.loginSubmit();
        return false;
      }
    })
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
