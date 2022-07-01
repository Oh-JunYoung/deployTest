import React from 'react';

import { Routes, BrowserRouter as Router, Route } from 'react-router-dom';
import {Dashboard} from './dashboard.js';
import {Nav} from './Nav';

function App() {
  return (
    <Router>
      <Nav />

      <Routes>
          <Route exact path='/' element={<Dashboard />} />

          <Route exact path='/dashboard' element={<Dashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
