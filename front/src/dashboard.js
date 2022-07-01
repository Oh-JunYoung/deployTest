import React, { useState, useEffect } from 'react';
import { fetchEventSource } from "@microsoft/fetch-event-source";
import styles from "./dashboard.css";

export const Dashboard = () => {
    const [dashboard, setDashboard] = useState(null);
    const [apiName, setApiName] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
          await fetchEventSource(
            `/server/dashboard`, 
            // `http://cowapi.com/dashboard`, 
            {method: "GET",
            headers: {Accept: "text/event-stream",},

            onopen(res) {
              if (res.ok && res.status === 200) {
                console.log("Connection made ", res);
              } else if (
                res.status >= 400 &&
                res.status < 500 &&
                res.status !== 429
              ) {
                console.log("Client side error ", res);
              }
            },

            onmessage(event) {
              const parsedData = JSON.parse(event.data);
              setDashboard(parsedData);
            
              const parseName = event.id;
              setApiName(parseName);
            },

            onclose() {
              console.log("Connection closed by the server");
            },

            onerror(err) {
              console.log("There was an error from server", err);
              setError(err);
            },});
        };

        fetchData();
      }, []);

    if (error) return <div>에러가 발생했습니다</div>;
    if (!dashboard) return null;
    
    return (
        <div>

        <h1>Dashboard</h1>

        <div style={styles}>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th colspan="4">API status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>API</td>
                            <td>updatedTime</td>
                        </tr>
                        <tr>
                            <td>{apiName}</td>
                            <td>{dashboard.updatedTime}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        </div>
    );
}