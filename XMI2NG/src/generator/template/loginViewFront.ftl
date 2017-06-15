<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h1>Please, Log In</h1>
            <form ng-submit="login()" class="form-horizontal">
                <fieldset>
                    <div class="form-group">
                        <label for="inputEmail" class="col-lg-2 control-label">Username</label>
                        <div class="col-lg-10">
                            <input type="text" class="form-control" id="inputEmail" placeholder="Username" ng-model="user.username">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword" class="col-lg-2 control-label">Password</label>
                        <div class="col-lg-10">
                            <input type="password" class="form-control" id="inputPassword" placeholder="Password" ng-model="user.password">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-2">
                            <input type="submit" class="btn btn-success" value="Log In" />
                        </div>
                    </div>
                </fieldset>
                <uib-alert ng-if="alert" type="{{alert.type}}" dismiss-on-timeout="{{alert.timeout}}">{{alert.msg}}</uib-alert>
            </form>
        </div>
    </div>
</div>
