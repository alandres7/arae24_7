$(document).ready($(function () {
    window.config = {
    	tenant: 'areametro.onmicrosoft.com	',
    	clientId: 'ef0a3b99-0017-49db-866c-30518fdda08c',
        postLogoutRedirectUri: window.location.origin,
        endpoints: {
            powerBi: "https://analysis.windows.net/powerbi/api"
        }, 
        popUp:true
        // cacheLocation: "localStorage"
    };

    var authContext = new AuthenticationContext(config);
    var isCallback = authContext.isCallback(window.location.hash);
    authContext.handleWindowCallback();

    if (isCallback && !authContext.getLoginError()) {
        window.location = authContext._getItem(authContext.CONSTANTS.STORAGE.LOGIN_REQUEST);
    }

    var user = authContext.getCachedUser();
    if (!user) {
        authContext.login();
    }
    else {
        console.log(user);

        authContext.acquireToken(config.endpoints.powerBi, function (error, token) {
            if (error || !token) {
                console.log("ADAL error occurred: " + error);
                return;
            }

            showReport(token);
        });
    }
}));

function showReport(token) {
    var models = window['powerbi-client'].models;
    $(".report").each(function (index) {
        var reportId = $(this).data('reportId');
        var groupId = $(this).data('groupId');
        var embedUrl = "https://app.powerbi.com/reportEmbed?reportId=" + reportId + "&groupId=" + groupId;
        
        var embedConfiguration = {
            type: 'report',
            accessToken: token,
            tokenType: models.TokenType.Aad,
            embedUrl: embedUrl,
            id: reportId,
            settings: {}
        };
        var report = powerbi.embed($(this).get(0), embedConfiguration);
    });
}
