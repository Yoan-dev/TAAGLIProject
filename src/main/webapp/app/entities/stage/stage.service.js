(function() {
    'use strict';
    angular
        .module('taagliProjectApp')
        .factory('Stage', Stage);

    Stage.$inject = ['$resource', 'DateUtils'];

    function Stage ($resource, DateUtils) {
        var resourceUrl =  'api/stages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDebut = DateUtils.convertLocalDateFromServer(data.dateDebut);
                        data.dateFin = DateUtils.convertLocalDateFromServer(data.dateFin);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateDebut = DateUtils.convertLocalDateToServer(data.dateDebut);
                    data.dateFin = DateUtils.convertLocalDateToServer(data.dateFin);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateDebut = DateUtils.convertLocalDateToServer(data.dateDebut);
                    data.dateFin = DateUtils.convertLocalDateToServer(data.dateFin);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
