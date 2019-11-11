<%@include file="includes/header.jsp" %>

<main role="main">

    <section class="jumbotron text-center" style="height: 300px;">
        <div class="container">
            <h1 class="jumbotron-heading" style="color: black;">Share your love of movies</h1>
        </div>
    </section>

    <div class="album py-5 bg-light">
        <div class="container">

            <div class="row">

                <c:forEach items="${films}" var="film">
                    <div class="col-md-3">
                        <div class="card mb-4 box-shadow">
                            <img class="card-img-top" alt="Thumbnail [100%x225]" style="height: 400px; width: 100%; display: block;" src="./assets/img/${film.moviePosterPath}" >
                            <div class="card-body" style="height: 130px">
                                <p class="card-text">${film.title}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <a class="btn btn-sm btn-outline-secondary" href="film?id=${film.id}">View</a>
                                        <a class="btn btn-sm btn-outline-secondary" href="home?page=${currentPage}&filmId=${film.id}">Like</a>
                                    </div>
                                    <div>
                                        <small class="text-muted">${film.runningTime} mins</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

</main>

<footer class="text-muted">
    <div class="container" style="margin-left: 40%;">
        <nav>
            <ul class="pagination">
                <!-- First page -->
                <li class="page-item">
                    <a class="page-link" href="home?page=1"><<</a>
                </li>
                <!-- Previous page -->
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="home?page=${currentPage - 1}"><</a>
                    </li>
                </c:if>
                <!-- 2 prev pages, curr page, 2 next pages -->
                <c:set var="begin" scope="request" value="${currentPage - 2}"/>
                <c:if test="${begin < 0}">
                    <c:set var="begin" scope="request" value="${0}"/>
                </c:if>
                <c:forEach begin="${begin}" end="${currentPage + 2}" var="i">
                    <c:if test="${i >= 1 && i <= nbPages}">
                        <li class="page-item">
                            <a class="page-link" href="home?page=${i}">${i}</a>
                        </li>
                    </c:if>
                </c:forEach>
                <!-- Next page -->
                <c:if test="${currentPage < nbPages}">
                    <li class="page-item">
                        <a class="page-link" href="home?page=${currentPage + 1}">></a>
                    </li>
                </c:if>
                <!-- Last page -->
                <li class="page-item">
                    <a class="page-link" href="home?page=${nbPages}">>></a>
                </li>
            </ul>
        </nav>
        <p class="float-right">
            <a href="#">Back to top</a>
        </p>
    </div>
</footer>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./assets/jquery-3.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="./assets/jquery-3.js"><\/script>')</script>
<script src="./assets/popper.js"></script>
<script src="./assets/bootstrap.js"></script>
<script src="./assets/holder.js"></script>

<svg xmlns="http://www.w3.org/2000/svg" width="348" height="225" viewBox="0 0 348 225" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs><text x="0" y="17" style="font-weight:bold;font-size:17pt;font-family:Arial, Helvetica, Open Sans, sans-serif">Thumbnail</text></svg></body></html>