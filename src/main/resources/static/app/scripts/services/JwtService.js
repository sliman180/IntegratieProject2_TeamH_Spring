(function (angular) {

    "use strict";

    function JwtService() {

        this.urlBase64Decode = function (str) {

            var output = str.replace(/-/g, '+').replace(/_/g, '/');

            switch (output.length % 4) {
                case 0:
                {
                    break;
                }
                case 2:
                {
                    output += '==';
                    break;
                }
                case 3:
                {
                    output += '=';
                    break;
                }
                default:
                {
                    throw 'Illegal base64url string!';
                }
            }

            return decodeURIComponent(escape(window.atob(output)));
        };

        this.decodeToken = function (token) {

            var parts = token.split('.');

            if (parts.length !== 3) {
                throw new Error('JWT must have 3 parts');
            }

            var decoded = this.urlBase64Decode(parts[1]);

            if (!decoded) {
                throw new Error('Cannot decode the token');
            }

            return JSON.parse(decoded);
        };

        this.getTokenExpirationDate = function (token) {

            var decoded = this.decodeToken(token);
            var d = new Date(0);

            if (typeof decoded.exp === "undefined") {
                return null;
            }

            d.setUTCSeconds(decoded.exp);

            return d;
        };

        this.isTokenExpired = function (token, offsetSeconds) {

            var d = this.getTokenExpirationDate(token);
            offsetSeconds = offsetSeconds || 0;

            if (d === null) {
                return false;
            }

            return !(d.valueOf() > (new Date().valueOf() + (offsetSeconds * 1000)));
        };

    }

    angular.module("kandoe").service("JwtService", JwtService);

}(window.angular));
