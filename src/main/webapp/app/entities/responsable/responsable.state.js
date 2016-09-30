(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('responsable', {
            parent: 'entity',
            url: '/responsable?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Responsables'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/responsable/responsables.html',
                    controller: 'ResponsableController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('responsable-detail', {
            parent: 'entity',
            url: '/responsable/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Responsable'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/responsable/responsable-detail.html',
                    controller: 'ResponsableDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Responsable', function($stateParams, Responsable) {
                    return Responsable.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'responsable',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('responsable-detail.edit', {
            parent: 'responsable-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsable-dialog.html',
                    controller: 'ResponsableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Responsable', function(Responsable) {
                            return Responsable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('responsable.new', {
            parent: 'responsable',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsable-dialog.html',
                    controller: 'ResponsableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                poste: null,
                                telephone: null,
                                mail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('responsable', null, { reload: 'responsable' });
                }, function() {
                    $state.go('responsable');
                });
            }]
        })
        .state('responsable.edit', {
            parent: 'responsable',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsable-dialog.html',
                    controller: 'ResponsableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Responsable', function(Responsable) {
                            return Responsable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('responsable', null, { reload: 'responsable' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('responsable.delete', {
            parent: 'responsable',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsable-delete-dialog.html',
                    controller: 'ResponsableDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Responsable', function(Responsable) {
                            return Responsable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('responsable', null, { reload: 'responsable' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
