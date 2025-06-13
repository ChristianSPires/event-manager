import React from "react";

function App() {
  const handleGoogleLogin = () => {
    window.location.href =
      "http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000";
  };

  const handleGithubLogin = () => {
    window.location.href =
      "http://localhost:8080/oauth2/authorization/github?redirect_uri=http://localhost:3000";
  };

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h1>Event Manager</h1>
      <button onClick={handleGoogleLogin}>Login com Google</button>
      <br />
      <br />
      <button onClick={handleGithubLogin}>Login com GitHub</button>
    </div>
  );
}

export default App;
