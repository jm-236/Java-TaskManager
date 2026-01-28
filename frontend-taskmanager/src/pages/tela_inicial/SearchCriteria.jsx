import React, { useState } from 'react';
import { Dropdown } from 'react-bootstrap';

const SearchCriteriaDropdown = () => {
  const [selectedCriteria, setSelectedCriteria] = useState('Todos');

  const searchCriteria = [
    'Todos', 
    // 'Data', 
    'Descrição', 
    'Nome da Tarefa', 
    'Categoria', 
    'Status'
  ];

  return (
    <Dropdown>
      <Dropdown.Toggle 
        variant="outline-secondary" 
        id="search-criteria-dropdown"
        className="ml-0 pl-0"
      >
        {selectedCriteria}
      </Dropdown.Toggle>

      <Dropdown.Menu>
        {searchCriteria.map((criteria) => (
          <Dropdown.Item 
            key={criteria} 
            onClick={() => setSelectedCriteria(criteria)}
          >
            {criteria}
          </Dropdown.Item>
        ))}
      </Dropdown.Menu>
    </Dropdown>
  );
};

export default SearchCriteriaDropdown;