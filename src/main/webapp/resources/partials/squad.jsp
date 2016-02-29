<div class="container">
    <div class="row">
        <div class="box">
            <div class="col-lg-12">
                <hr>
                <h2 class="intro-text text-center">Players</h2>
                <hr>
                <div ng-repeat="player in players">
                    <a href="#players/1"><div class="fixture-summary">
                            <div>{{player.firstName}} {{player.lastName}}</div>
                        </div></a>
                    <hr>
                </div>
            </div>
        </div>
    </div>
</div>