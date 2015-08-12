<div class="container">
    <div class="row">
        <div class="box">
            <div class="col-lg-12">
                <hr>
                <h2 class="intro-text text-center">Fixtures</h2>
                <hr>
                <div ng-repeat="fixture in fixtures">
                    <a href="#fixtures/{{fixture.id}}"><div class="fixture-summary">
                            <div class="flag-left">
                                <img class="img-circle" ng-src="/resources/images/flags/medium/{{fixture.homeTeamFlag}}" />
                            </div>
                            <div class="details">
                                <div class="teams">{{fixture.homeTeamName}} v {{fixture.awayTeamName}}</div>
                                <div class="date">{{fixture.date | date : 'EEE dd MMM yyyy, h:mma'}}</div>
                            </div>
                            <div class="flag-right">
                                <img class="img-circle" ng-src="/resources/images/flags/medium/{{fixture.awayTeamFlag}}" />
                            </div>
                        </div></a>
                    <hr>
                </div>
            </div>
        </div>
    </div>
</div>