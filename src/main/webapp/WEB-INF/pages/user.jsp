<%@include file="includes/header.jsp" %>

<main role="main">
  <div class="album py-5 bg-light">
    <div class="container">

      <div class="row">
        <div class="col-md-6">
          <div class="card mb-4 box-shadow">
            <form style="padding: 10px;">
              <div class="form-row">
                <div class="col">
                  <label for="userName">Username</label>
                  <input type="text" class="form-control" id="userName" disabled="disabled" value="${user.username}">
                </div>
                <div class="col">
                  <label for="email">Email address</label>
                  <input type="email" class="form-control" id="email" disabled="disabled" value="${user.email}">
                </div>
              </div>
              <div class="form-row">
                <div class="col">
                  <label for="firstName">First name</label>
                  <input type="text" class="form-control" id="firstName" disabled="disabled" value="${user.firstName}">
                </div>
                <div class="col">
                  <label for="lastName">Last name</label>
                  <input type="text" class="form-control" id="lastName" disabled="disabled" value="${user.lastName}">
                </div>
              </div>
            </form>
          </div>
        </div>

        <div class="col-md-6">
          <div class="card mb-4 box-shadow">
            <div class="card-body" style="height: 100%">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">Liked films</th>
                    <th scope="col" style="width: 60%;"></th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                <c:forEach items="${films}" var="film">
                  <tr>
                    <th scope="row"><img class="card-img-top" alt="Thumbnail [100%x225]" style="height: 150px; width: 100%; display: block;" src="./assets/img/${film.moviePosterPath}" ></th>
                    <td class="card-text">${film.title}</td>
                    <td><a class="btn btn-sm btn-outline-secondary" href="film?id=${film.id}">View</a></td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>

<footer class="text-muted">
  <div class="container">
    <p class="float-right">
      <a href="#">Back to top</a>
    </p>
  </div>
</footer>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./assets/jquery-3.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
<script src="./assets/popper.js"></script>
<script src="./assets/bootstrap.js"></script>
<script src="./assets/holder.js"></script>

<svg xmlns="http://www.w3.org/2000/svg" width="348" height="225" viewBox="0 0 348 225" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs><text x="0" y="17" style="font-weight:bold;font-size:17pt;font-family:Arial, Helvetica, Open Sans, sans-serif">Thumbnail</text></svg></body></html>