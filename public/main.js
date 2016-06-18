// ---->   HOURS OF HAPPINESS
// ---->   INITIALIZE APP WHEN DOCUTMENT IS READY
// ---->    created by: Caleb Bodtorf :: 6/18/16

$('document').ready(function(){
  app.init();
  initLocalClocks();
  moveSecondHands();
})

// ---->    OBJECT MODEL app

var app = {

  // ---->    urls from database

  url: {
    login: "/login",
    review: "/get-reviews",
    bars: "/get-bars",
    logout: "/log-out",
    createbar: "/create-bar",
    getbars: "/get-bars",
    createreview: "/create-review",
    editreview: "/edit-review",
    deletebar: "/delete-bar/:id",
    deletereview: "/delete-review"
  },

  // ---->     stores username to reduce calls to server

  user: '',
  init: function(){
    app.events();
    app.styling();
  },

  // ---->    login function to be used on click and enter events.

  loginSubmit: function(){
    event.preventDefault();
    app.user = $('input[name=username]').val();
    var $password = $('input[type=password]').val();
    console.log("shit submit", app.user);
    if($password === 'poop') {
      var objToSend = {username: app.user};
      app.createuser(JSON.stringify(objToSend));
      $('.login').fadeOut();
      $('.hoh-main').removeClass("hidden").hide().fadeIn(2000);
    } else {
      $('input[type=password]').val('').attr('placeholder','wrong password!');
    }
  },

  // ---->    STYLING
  // ---->    replaces default w/ username

  styling: function(){
    $('.welcome').text(`${app.user}, Welcome to The Secret Society of Happiness`);

  },
  events: function(){

    // ---->    SUBMIT on CLICK
    $('button[type=button]').on('click', function(event){
      app.loginSubmit();
      app.styling();
    })

    // ---->    SUBMIT on ENTER
    $('.login').on('keypress click', function(event){
    var keyCode = event.keyCode || event.which;
      if (keyCode === 13) {
        app.loginSubmit();
        app.styling();
        return false;
      }
    })
  },

    // ----> CLICK BAR to REVIEWPAGE
    $('.bar').on('click', function(event){
      $('.bars').fadeOut();
      app.readReview();
    });





  // ---->    AJAX REQUESTS
  // ---->


  createuser: function(stuff){
    $.ajax({
      url: app.url.login,
      data: stuff,
      method: "POST",
      success: function(data){
        console.log("X gonna give it to ya::creating", data);
        app.readBar();
      },
      error: function(err) {
        console.log('dang son',err)
      }
    })
  },

  readBar: function(){
    $.ajax({
      url: app.url.bars,
      method: "GET",
      success: function(data){
        console.log("X gonna give it to ya::reading", data);
        data = JSON.parse(data);
        $('.bars').html('');
        data.forEach(function(element,idx){
          var barString = app.htmlGen(templates.barSummary, element)
          $('.bars').append(barString);
        })
      },
      error: function(err) {
        console.log('dang son',err)
      }
    })
  },

  readReview: function(){
    $.ajax({
      url: app.url.review,
      method: "GET",
      success: function(data){
        console.log("X gonna give it to ya::reading", data);
        data = JSON.parse(data);
        $('.bars').html('');
        data.forEach(function(element,idx){
          var barString = app.htmlGen(templates.review, element)
          $('.bars').append(barString);
        })
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

  // ---->    MERGES TEMPLATES.JS W/ DATA
  // ---->    arguement example: templates.summary

  templater: function(template){
    return _.template(template);
  },
  htmlGen: function (template, data){
    var tmpl = app.templater(template);
    return tmpl(data);
  }
}
