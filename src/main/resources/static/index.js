angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/web-market/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webUser = {username: $scope.user.username, token: response.data.token};
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
    };

    $scope.clearUser = function () {
        delete $localStorage.webUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.webUser) {
            return true;
        } else {
            return false;
        }
    };

    if ($localStorage.webUser) {
        try {
            let jwt = $localStorage.webUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token id expired!");
                delete $localStorage.webUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {}
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webUser.token;
    }

    $scope.checkAuth = function () {
        $http.get('http://localhost:8189/web-market/auth_check').then(function (response) {
            alert(response.data.value);
        });
    };

    $scope.loadProducts = function () {
        $http.get('http://localhost:8189/web-market/api/v1/products').then(function (response) {
            $scope.productList = response.data;
        });
    };

    $scope.showProductInfo = function (productId) {
        $http.get('http://localhost:8189/web-market/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    };

    $scope.loadCart = function () {
        $http.get('http://localhost:8189/web-market/api/v1/cart').then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:8189/web-market/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.removeFromCart = function (productId) {
        $http.get('http://localhost:8189/web-market/api/v1/cart/remove/' + productId).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:8189/web-market/api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.loadProducts();
    $scope.loadCart();
})