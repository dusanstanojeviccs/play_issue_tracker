app.controller('SingleIssueController', function($scope, $http) {
    $scope.status = Preset.status;
    $scope.issueChange = {
        id: Preset.id
    };

    $scope.notSolved = function() {
        $http.post(Routes.changeIssueStatus, JSON.stringify({id: Preset.id, newStatus:"Not Solved"})).success(function(data, status) {
            if (data==="Ok") {
                $scope.status = "Not Solved";              
            }
        }).error(function(data, status) {
            $scope.error = true;
        });
    };
    $scope.solved = function() {
        $http.post(Routes.changeIssueStatus, JSON.stringify({id: Preset.id, newStatus:"Solved"})).success(function(data, status) {
            if (data==="Ok") {
                $scope.status = "Solved";                            
            }
        }).error(function(data, status) {
            $scope.error = true;
        });
    };
});
