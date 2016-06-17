var templates = {
  summary:
  `
  <div class='summary img-wrapper' data-id='<%= _id %>'>
    <img class="bar" src="<%= image-url %>" alt="" />
    <span> <%= bar-name %> </span>
  </div>
  `,
  fullView:
  `
  <div class='fullView =' data-id='<%= _id %>'>
    <img class="bar" src="<%= image-url %>" alt="" />
    <h3> <%= bar-name %> </h3>
    <span><%= username %></span>
  </div>
  `
}
