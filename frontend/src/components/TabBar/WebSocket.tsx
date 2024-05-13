import { Client } from '@stomp/stompjs';
import { useEffect, useState } from 'react';

export default function WebSocket() {
    const [wsClient, setWsClient] = useState<Client>();
    //handshake
    useEffect(() => {
        const newClient = new Client({
            brokerURL: `ws://localhost:8084/place`,
            debug: function (str) {
                console.log(str);
            },
            reconnectDelay: 5000,
        });

        newClient.onConnect = (frame) => {
            console.log('hello');
            console.log(frame);
        };

        newClient.activate();

        console.log(newClient);

        setWsClient(newClient);
    }, []);

    return <div className="hidden"></div>;
}
